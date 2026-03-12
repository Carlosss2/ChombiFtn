package com.carlos.chombi.core.hardware.data

import android.content.Context
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.carlos.chombi.core.hardware.domain.BiometricManager
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AndroidBiometricManager @Inject constructor() : BiometricManager {

    override suspend fun registerBiometric(context: Context, challengeJson: String): Result<String> {
        // En un flujo real, "registrar" y "autenticar" a nivel biométrico local es casi lo mismo:
        // le pides al usuario que ponga la huella. La diferencia principal radicará en tu backend
        // (qué haces con el challengeJson).
        return showBiometricPrompt(
            context = context,
            title = "Configurar Biometría",
            subtitle = "Usa tu huella para acceder más rápido",
            challengeJson = challengeJson
        )
    }

    override suspend fun authenticateBiometric(context: Context, challengeJson: String): Result<String> {
        return showBiometricPrompt(
            context = context,
            title = "Iniciar Sesión",
            subtitle = "Confirma tu identidad para continuar",
            challengeJson = challengeJson
        )
    }

    private suspend fun showBiometricPrompt(
        context: Context,
        title: String,
        subtitle: String,
        challengeJson: String
    ): Result<String> = suspendCancellableCoroutine { continuation ->

        // La API biométrica necesita una Activity para mostrar el UI del sistema
        val activity = context as? FragmentActivity
        if (activity == null) {
            continuation.resume(Result.failure(Exception("El contexto debe ser un FragmentActivity para mostrar el diálogo biométrico.")))
            return@suspendCancellableCoroutine
        }

        val executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // Aquí podrías usar CryptoObject si tuvieras firmas criptográficas.
                    // Por ahora, si pasa, devolvemos éxito.
                    if (continuation.isActive) {
                        continuation.resume(Result.success("¡Autenticación exitosa! Challenge: $challengeJson"))
                    }
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    if (continuation.isActive) {
                        continuation.resume(Result.failure(Exception("Error $errorCode: $errString")))
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Nota: onAuthenticationFailed se llama cuando pone una huella incorrecta.
                    // El diálogo no se cierra automáticamente aquí, el usuario puede intentar de nuevo.
                    // Por lo general no cancelamos la corrutina aquí para dejarle reintentar,
                    // a menos que quieras controlar los intentos máximos manualmente.
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setNegativeButtonText("Cancelar")
            .setAllowedAuthenticators(BIOMETRIC_STRONG) // Solo huella/rostro seguros
            .build()

        // Si la corrutina se cancela por otro motivo, cerramos el diálogo
        continuation.invokeOnCancellation {
            biometricPrompt.cancelAuthentication()
        }

        // Lanzamos el diálogo
        biometricPrompt.authenticate(promptInfo)
    }
}