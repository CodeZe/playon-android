package ga.chrom_web.player.multiplayer.ui.player

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import ga.chrom_web.player.multiplayer.R

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val SHARE_LINK = "share_link"
    }

    private var mPlayerFragment: PlayerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        if (savedInstanceState == null) {
            mPlayerFragment = PlayerFragment()
            // if intent is not empty, that means someone shared video
            intent?.extras?.getString(SHARE_LINK).let { youtubeLink ->
                val bundle = Bundle()
                bundle.putString(SHARE_LINK, youtubeLink)
                mPlayerFragment?.arguments = bundle
            }

            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, mPlayerFragment)
                    .commit()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // activity in singleInstance mode
        // so this is the only way to get intent
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        intent?.extras?.getString(SHARE_LINK)?.let { link ->
            mPlayerFragment?.receiveShare(link)
        }
    }

    override fun onBackPressed() {
        val playerOnBackPressed = mPlayerFragment?.onBackPressed()
        if (playerOnBackPressed == null || !playerOnBackPressed) {
            super.onBackPressed()
        }
    }
}
