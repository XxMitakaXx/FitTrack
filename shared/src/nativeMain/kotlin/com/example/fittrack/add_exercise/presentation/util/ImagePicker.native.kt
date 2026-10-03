package com.example.fittrack.add_exercise.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIWindow
import platform.darwin.NSObject
import platform.posix.memcpy

actual class ImagePicker(
    private val onLaunch: () -> Unit
) {
    actual fun launch() {
        onLaunch()
    }
}

@Composable
actual fun rememberImagePicker(onImagePicked: (ByteArray?) -> Unit): ImagePicker {
    val imagePickerController = remember { UIImagePickerController() }

    val delegate = remember {
        object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val image = didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as? UIImage
                if (image != null) {
                    val imageData = UIImageJPEGRepresentation(image, compressionQuality = 1.00)
                    onImagePicked(imageData?.toByteArray())
                } else {
                    onImagePicked(null)
                }
                picker.dismissViewControllerAnimated(flag = true, completion = null)
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                onImagePicked(null)
                picker.dismissViewControllerAnimated(flag = true, completion = null)
            }
        }

    }

    return remember {
        ImagePicker {
            imagePickerController.setSourceType(sourceType = UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypePhotoLibrary)
            imagePickerController.setDelegate(delegate = delegate)

            val window = UIApplication.sharedApplication.windows.firstOrNull { (it as UIWindow).isKeyWindow() } as? UIWindow
            window?.rootViewController?.presentViewController(viewControllerToPresent = imagePickerController, animated = true, completion = null)
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun NSData.toByteArray(): ByteArray {
    val size = length.toInt()
    val byteArray = ByteArray(size)
    if (size > 0) {
        byteArray.usePinned { pinned ->
            memcpy(__dst = pinned.addressOf(index = 0), __src = this.bytes, __n = this.length)
        }
    }
    return byteArray
}