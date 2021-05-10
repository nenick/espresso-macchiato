package de.nenick.android.emulator.settings

import com.android.sdklib.AndroidVersion
import de.nenick.android.emulator.tool.EmulatorInstance

object ObsoletePackages {

    fun configure(instance: EmulatorInstance) {
        disableGeofence(instance)
        disableNearby(instance)
        disableGmsStuff(instance)
        disableLocation(instance)

        // Disabling "com.android.phone/..telephony.." services didn't work.
        // Disabling "com.android.server.telecom/.." services didn't work.
    }

    private fun disableGeofence(instance: EmulatorInstance) {
        instance.disableService(
            "android/.hardware.location.GeofenceHardwareService",
            "com.google.android.gms/com.google.android.location.geofencer.service.GeofenceProviderService",
        )
    }

    private fun disableNearby(instance: EmulatorInstance) {
        instance.disableService(
            "com.google.android.gms/.nearby.discovery.service.DiscoveryService",
            "com.google.android.gms/.nearby.messages.service.NearbyMessagesService",
            "com.google.android.gms/com.google.location.nearby.direct.service.NearbyDirectService",
        )
    }

    private fun disableGmsStuff(instance: EmulatorInstance) {
        instance.disableService(
            "com.google.android.gms/.auth.setup.devicesignals.LockScreenService",
            "com.google.android.gms/.auth.account.authenticator.GoogleAccountAuthenticatorService",

            // This single service has a huge performance impact on some android versions
            "com.google.android.gms/.chimera.GmsIntentOperationService",

            "com.google.android.gms/.chimera.GmsApiService",
            "com.google.android.gms/.chimera.PersistentApiService",
            "com.google.android.gms/.chimera.PersistentBoundBrokerService",
            "com.google.android.gms/.chimera.PersistentDirectBootAwareApiService",
            "com.google.android.gms/.chimera.PersistentIntentOperationService",

            "com.google.android.gms/.clearcut.debug.ClearcutDebugDumpService",
            "com.google.android.gms/.common.stats.GmsCoreStatsService",
            "com.google.android.gms/.deviceconnection.service.DeviceConnectionWatcherService",
            "com.google.android.gms/.fido.fido2.pollux.CableAuthenticatorService",

            "com.google.android.gms/.gcm.GcmService",
            "com.google.android.gms/.gcm.nts.SchedulerService",

            "com.google.android.gms/.thunderbird.EmergencyPersistentService"
        )

        // Disabling this stuff results sometimes in ShellCommandUnresponsiveException on some android versions
        if (instance.version >= AndroidVersion.VersionCodes.KITKAT) {
            instance.disableService("com.google.android.gms/com.google.android.contextmanager.service.ContextManagerService")
        }
    }

    private fun disableLocation(instance: EmulatorInstance) {
        instance.disableService(
            "com.google.android.gms/.location.persistent.LocationPersistentService",

            "com.google.android.gms/com.google.android.location.fused.FusedLocationService",
            "com.google.android.gms/com.google.android.location.geocode.GeocodeService",
            "com.google.android.gms/com.google.android.location.internal.server.GoogleLocationService",
            "com.google.android.gms/com.google.android.loca¬tion.internal.server.HardwareArProviderService",
            "com.google.android.gms/com.google.android.location.network.NetworkLocationService",
        )

        // Disabling this stuff force continuously appearing system crash dialog on some android versions.
        if (instance.version >= AndroidVersion.VersionCodes.KITKAT_WATCH) {
            instance.disableService("com.google.android.gms/com.google.android.location.internal.GoogleLocationManagerService")
        }
    }
}