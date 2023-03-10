package khan.uz.tictactoe

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import khan.uz.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    enum class Type {
        NOUGHT, CROSS
    }

    private var firstType = Type.CROSS
    private var currentType = Type.CROSS

    private var boardList = mutableListOf<Button>()

    private var cross = 0
    private var nought = 0
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(2000)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initBoard()

    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTrapped(view: View) {

        if (view !is Button)
            return
        addToBoar(view)

        if (checkForVictory(NOUGHT)) {
            nought++
            result("0 Win ! ! !")
        } else if (checkForVictory(CROSS)) {
            cross++
            result("X Win ! ! !")
        } else if (fullBoard()) {
            result("Draw")
        }

    }

    private fun checkForVictory(s: String): Boolean {


        //h
        if (match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s)) {
            return true
        } else if (match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s)) {
            return true
        } else if (match(binding.c1, s) && match(binding.c2, s) && match(binding.c1, s)) {
            return true
        }

        //v
        else if (match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s)) {
            return true
        } else if (match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s)) {
            return true
        } else if (match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s)) {
            return true
        }


        //d
        else if (match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s)) {
            return true
        } else if (match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s)) {
            return true
        }





        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {

        val message = "\n0: $nought\n\nX: $cross"
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            { _, _ ->
                resetBoard()

            }
            .setNegativeButton("Exit") { _, _ ->
                finish()

            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard() {

        for (button in boardList) {
            button.text = ""
        }
        if (firstType == Type.NOUGHT)
            firstType = Type.CROSS
        else if (firstType == Type.CROSS)
            firstType = Type.NOUGHT

        currentType = firstType
        setTurnLabel()

    }

    private fun fullBoard(): Boolean {

        for (button in boardList) {
            if (button.text == "") {
                return false
            }
        }

        return true

    }


    @SuppressLint("ResourceAsColor")
    private fun addToBoar(button: Button) {

        if (button.text != "")
            return
        if (currentType == Type.NOUGHT) {
            val anim = AnimationUtils.loadAnimation(this, R.anim.anim_tic)
            button.startAnimation(anim)

            button.setTextColor(Color.BLUE)
            button.text = NOUGHT
            currentType = Type.CROSS

        } else if (currentType == Type.CROSS) {
            val anim = AnimationUtils.loadAnimation(this, R.anim.anim_tic)
            button.startAnimation(anim)
            button.setTextColor(Color.RED)
            button.text = CROSS
            currentType = Type.NOUGHT
        }

        setTurnLabel()
    }

    private fun setTurnLabel() {
        var gameText = ""
        if (currentType == Type.CROSS) {
            gameText = "Game: $CROSS"
        } else if (currentType == Type.NOUGHT) {
            gameText = "Game: $NOUGHT"
        }
        binding.txt.text = gameText


    }

    companion object {
        const val NOUGHT = "0"
        const val CROSS = "X"
    }


}