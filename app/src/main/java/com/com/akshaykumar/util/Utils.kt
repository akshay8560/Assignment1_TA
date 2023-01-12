package com.com.akshaykumar.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.com.akshaykumar.R

import com.com.akshaykumar.data.model.Workshop


class Utils {

    companion object {
        fun dialog(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.layout_custom_progress_dialog)
            return dialog
        }

        fun workShop(): ArrayList<Workshop> {
            val list = ArrayList<Workshop>()
            list.add(
                Workshop(
                    0,
                    "Android Development",
                    "ADDA247",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "01/01/2021"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Web Development",
                    "ABC",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "02/02/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "PHP Development",
                    "CDF",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "03/03/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Flutter Development",
                    "MNO",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "04/04/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Android Development",
                    "UTV",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "05/05/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "React Development",
                    "Jkl",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "06/04/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Java Development",
                    "UFT",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "04/04/2022"
                )
            )

            list.add(
                Workshop(
                    0,
                    "Game Development",
                    "SDC",
                    "Lorem ipsum is placeholder text commonly used in the graphic, print, and publishing industries for previewing layouts and visual mockups.",
                    "07/07/2022"
                )
            )

            return list
        }
    }
}