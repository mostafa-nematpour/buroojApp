package ir.burooj.basij.video

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import ir.burooj.basij.R


class VideoActivity : AppCompatActivity() {

    var player: VideoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        player = findViewById(R.id.video_view)

        val mediaController = MediaController(this)

        player?.setMediaController(mediaController)

        val uri = Uri.parse("https://burooj.ir/test.mp4")
        player?.setVideoURI(uri)
        player?.start()





        player?.setOnPreparedListener(MediaPlayer.OnPreparedListener { mediaPlayer ->

            fun onPrepared(mp: MediaPlayer?) {
                Toast.makeText(this, "رسید", Toast.LENGTH_SHORT).show()
            }
            mediaPlayer.setOnVideoSizeChangedListener { mediaPlayer, i, i1 ->
                mediaController.setAnchorView(player)


                if (mediaController.isShowing) {
                    // Do something
                } else {
                    mediaController.show(3000);
                }
            }
        })


        player?.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            Toast.makeText(this, "ویدئو به پایان رسید", Toast.LENGTH_SHORT).show()
        })

//        player?.setOnErrorListener(MediaPlayer.OnErrorListener { mediaPlayer, i, i1 ->
//            Toast.makeText(this, "اشکالی در پخش ویدئو وجود دارد", Toast.LENGTH_SHORT).show()
//            false
//        })




    }


    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("MyVideoPosition", player!!.currentPosition)
        player!!.pause()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val videoPosition = savedInstanceState.getInt("MyVideoPosition");
        player!!.seekTo(videoPosition);
    }

}