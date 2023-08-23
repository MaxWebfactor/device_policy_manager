import android.app.admin.DeviceAdminReceiver
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.UserManager
import android.util.Log
import androidx.activity.ComponentActivity

class DeviceAdmin : DeviceAdminReceiver() {

    private val TAG = "DeviceOwnerReceiver"


    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        Log.d(TAG, "onProfileProvisioningComplete")
        val manager = context.getSystemService(ComponentActivity.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val componentName = ComponentName(context.applicationContext, DeviceAdmin::class.java)

        manager.setProfileName(componentName, context.resources.getString(R.string.profile));

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG, "App is device owner")
        val dpm = getManager(context)
        val adminName = getWho(context)
        dpm.addUserRestriction(adminName, UserManager.DISALLOW_SYSTEM_ERROR_DIALOGS)
    }
}