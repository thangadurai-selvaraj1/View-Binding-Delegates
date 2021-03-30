import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <reified T : ViewBinding> BottomSheetDialogFragment.viewBinding() = BottomSheetViewBindingDelegate(T::class.java)

class BottomSheetViewBindingDelegate<T : ViewBinding>(private val bindingClass: Class<T>) : ReadOnlyProperty<BottomSheetDialogFragment, T> {
   
    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: BottomSheetDialogFragment, property: KProperty<*>): T {
        binding?.let { return it }

      
        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)

       
        val invokeLayout = inflateMethod.invoke(null, LayoutInflater.from(thisRef.context)) as T

        return invokeLayout.also { this.binding = it }
    }
}