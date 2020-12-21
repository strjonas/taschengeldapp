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
                title = resources.getString(R.string.Welcome),
                description = resources.getString(R.string.welcomescreen),
                backgroundDrawable = R.drawable.slide_background,
            imageDrawable = R.drawable.logoforeground

        ))
        addSlide(AppIntroFragment.newInstance(
                title = resources.getString(R.string.Welcome2),
                description = resources.getString(R.string.MainPage),
                imageDrawable = R.drawable.overview,
                backgroundDrawable = R.drawable.slide_background

        ))
        addSlide(AppIntroFragment.newInstance(
                title = resources.getString(R.string.Welcome3),
                description = resources.getString(R.string.Addingachild),
                imageDrawable = R.drawable.addchild,
                backgroundDrawable = R.drawable.slide_background

        ))
        addSlide(AppIntroFragment.newInstance(
                title =resources.getString(R.string.Welcome4),
                description = resources.getString(R.string.ChildsLog),
                imageDrawable = R.drawable.childlog,
                backgroundDrawable = R.drawable.slide_background
        ))
        addSlide(AppIntroFragment.newInstance(
                title = resources.getString(R.string.Welcome5),
                description = resources.getString(R.string.Addingtransaction),
                imageDrawable = R.drawable.add,
                backgroundDrawable = R.drawable.slide_background


        ))
        addSlide(AppIntroFragment.newInstance(
                title = resources.getString(R.string.Welcome6),
                description = resources.getString(R.string.Letsgetstarted),
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