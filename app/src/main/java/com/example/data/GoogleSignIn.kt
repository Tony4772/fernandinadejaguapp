package com.example.data

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.NoCredentialException
import com.example.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

object GoogleSignIn {
  private fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
      if (ctx is Activity) return ctx
      ctx = ctx.baseContext
    }
    return null
  }

  suspend fun requestIdToken(context: Context): String {
    val activity = context.findActivity() ?: context
    val credentialManager = CredentialManager.create(activity)
    val webClientId = context.getString(R.string.default_web_client_id)
    val googleIdOption = GetGoogleIdOption.Builder()
      .setFilterByAuthorizedAccounts(false)
      .setServerClientId(webClientId)
      .setAutoSelectEnabled(false)
      .build()
    val request = GetCredentialRequest.Builder()
      .addCredentialOption(googleIdOption)
      .build()
    val result = credentialManager.getCredential(activity, request)
    val credential = result.credential
    if (credential is CustomCredential &&
      credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
    ) {
      return GoogleIdTokenCredential.createFrom(credential.data).idToken
    }
    error("No se pudo obtener las credenciales de Google")
  }

  suspend fun clearCredentialState(context: Context) {
    try {
      val activity = context.findActivity() ?: context
      val credentialManager = CredentialManager.create(activity)
      credentialManager.clearCredentialState(ClearCredentialStateRequest())
    } catch (_: Throwable) {}
  }

  fun messageFor(error: Throwable): String? {
    return when (error) {
      is GetCredentialCancellationException -> null
      is NoCredentialException -> "No hay una cuenta de Google activa en este dispositivo"
      else -> error.localizedMessage?.takeIf { it.isNotBlank() } ?: "No se pudo entrar con Google"
    }
  }
}
