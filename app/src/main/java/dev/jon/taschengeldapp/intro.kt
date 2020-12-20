package dev.jon.taschengeldapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Color.blue
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro
import com.github.appintro.AppIntroFragment
import com.github.appintro.AppIntroPageTransformerType

class intro : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure you don't call setContentView!
        supportActionBar?.hide()
        setTransformer(AppIntroPageTransformerType.Depth)
        // Call addSlide passing your Fragments.
        // You can use AppIntroFragment to use a pre-built fragment
        addSlide(AppIntroFragment.newInstance(
                title = "Welcome...",
                description = "This is a tutorial to show you the functionality of this app!",
                backgroundDrawable = R.drawable.slide_background2,
            imageDrawable = R.drawable.logoforeground

        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Main Page:",
                description = "Her you can add new children to your collection and view the ones you already added!",
                imageDrawable = R.drawable.overview,
                backgroundDrawable = R.drawable.slide_background

        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Adding a child: ",
                description = "When clicking on the blue plus button you saw on the previous page, you get to this page. Her you specify the settings for your child and add it to your collection! ",
                imageDrawable = R.drawable.addchild,
                backgroundDrawable = R.drawable.slide_background

        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Child's Log: ",
                description = "When you add transactions, you can View them at this Screen. You get there by clicking on the name of your child on the Main Page!",
                imageDrawable = R.drawable.childlog,
                backgroundDrawable = R.drawable.slide_background
        ))
        addSlide(AppIntroFragment.newInstance(
                title = "Adding transaction: ",
                description = "Here you can see how adding a transaction looks!",
                imageDrawable = R.drawable.add,
                backgroundDrawable = R.drawable.slide_background


        ))
        addSlide(AppIntroFragment.newInstance(
                title = "...Let's get started!",
                description = "(The pocket money you select for your child, gets" +
                "paid every month/week (depending on what you selected) to your childs account, and you can click the \"pay\" button to log that you gave it to your child!)",
                backgroundDrawable = R.drawable.slide_background2


        ))
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        // Decide what to do when the user clicks on "Skip"
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        // Decide what to do when the user clicks on "Done"
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }
}