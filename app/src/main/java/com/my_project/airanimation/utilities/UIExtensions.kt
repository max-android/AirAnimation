
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import com.my_project.airanimation.data.entities.Loc
import com.my_project.airanimation.presentation.main.MainActivity


val Fragment.mainActivity: MainActivity?
    get() = activity as MainActivity?


fun View.visible() {
    if (visibility != View.VISIBLE)
        visibility = View.VISIBLE
}

fun View.invisible() {
    if (visibility != View.INVISIBLE)
        visibility = View.INVISIBLE
}

fun View.gone() {
    if (visibility != View.GONE)
        visibility = View.GONE
}

fun Intent.addClearStackFlags(): Intent {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
            Intent.FLAG_ACTIVITY_NO_ANIMATION or
            Intent.FLAG_ACTIVITY_CLEAR_TASK
    return this
}

fun Activity.hideSoftKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showSoftKeyboard(editTextAuthPhoneNumber: EditText) {
    val imm =
        editTextAuthPhoneNumber.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editTextAuthPhoneNumber, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.getScreenWidth(): Int {
    val size = Point()
    (this as Activity).windowManager.defaultDisplay.getSize(size)
    return size.x
}


fun Context.createMarkerOptions(view: View, location: Loc, zIndex:Boolean): MarkerOptions {
    val icon = IconGenerator(this)
    icon.apply {
        setContentView(view)
        setBackground(null)
    }
    val option = MarkerOptions()
    option.apply {
        position(LatLng(location.lat, location.lon))
        icon(BitmapDescriptorFactory.fromBitmap(icon.makeIcon()))
        if(zIndex){
            zIndex(2f)
            anchor(0.5f, 0.5f)
        }
    }
    return option
}




