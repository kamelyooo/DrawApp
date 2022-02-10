package com.example.drawingapp.UI

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.drawingapp.R
import com.example.drawingapp.Enums.SelectedColor
import com.example.drawingapp.Enums.ShapeType

import com.example.drawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val binding get() = _binding!!
    private var _binding: ActivityMainBinding? = null

    companion object {

        var mCurrentShape: ShapeType = ShapeType.SMOOTHLINE
        var mPath = Path()
        var mPaint = Paint()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getSelectedColor().observe(this, Observer {
            selectedColor(it)
        })
        viewModel.getSelectedShape().observe(this, Observer {
            mCurrentShape = it
            selectBtnsBackground(it)
        })

        binding.arrowBtn.setOnClickListener {
            viewModel.setShapeSelected(ShapeType.LINE)
        }
        binding.smoothBtn.setOnClickListener {
            viewModel.setShapeSelected(ShapeType.SMOOTHLINE)
        }
        binding.circleBtn.setOnClickListener {
            viewModel.setShapeSelected(ShapeType.CIRCLE)
        }
        binding.rectBtn.setOnClickListener {
            viewModel.setShapeSelected(ShapeType.RECTANGLE)
        }
        binding.colorBtn.setOnClickListener {
            showColors()
        }
        binding.redBtn.setOnClickListener {
            mPath = Path()
            viewModel.setColorSelected(SelectedColor.RED)
            hideColors()
        }
        binding.blackBtn.setOnClickListener {
            mPath = Path()
            viewModel.setColorSelected(SelectedColor.BLACK)
            hideColors()

        }
        binding.greenBtn.setOnClickListener {
            mPath = Path()
            viewModel.setColorSelected(SelectedColor.GREEN)
            hideColors()

        }
        binding.blueBtn.setOnClickListener {
            mPath = Path()
            viewModel.setColorSelected(SelectedColor.BLUE)
            hideColors()
        }
    }

    private fun selectedColor(selectedColor: SelectedColor) = when (selectedColor) {
        SelectedColor.BLACK -> {
            setColorToPaint(resources.getColor(R.color.black))
        }
        SelectedColor.RED -> {
            setColorToPaint(resources.getColor(R.color.red))
        }
        SelectedColor.GREEN ->
            setColorToPaint(resources.getColor(R.color.green))
        SelectedColor.BLUE ->
            setColorToPaint(resources.getColor(R.color.blue))
    }

    private fun setColorToPaint(colorr: Int) {
        mPaint.apply {
            color = colorr
        }
    }

    private fun showColors() {
        binding.colorContainer.visibility = View.VISIBLE
    }

    private fun hideColors() {
        binding.colorContainer.visibility = View.GONE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun selectBtnsBackground(shapeType: ShapeType) {
        if (shapeType == ShapeType.SMOOTHLINE) {
            binding.smoothBtn.apply {
                background = resources.getDrawable(R.drawable.selected_shape)
            }
        } else {
            binding.smoothBtn.apply {
                background = resources.getDrawable(R.drawable.unselected_shape)
            }
        }
        if (shapeType == ShapeType.LINE) {
            binding.arrowBtn.apply {
                background = resources.getDrawable(R.drawable.selected_shape)
            }
        } else {
            binding.arrowBtn.apply {
                background = resources.getDrawable(R.drawable.unselected_shape)
            }
        }
        if (shapeType == ShapeType.CIRCLE) {
            binding.circleBtn.apply {
                background = resources.getDrawable(R.drawable.selected_shape)
            }
        } else {
            binding.circleBtn.apply {
                background = resources.getDrawable(R.drawable.unselected_shape)
            }
        }
        if (shapeType == ShapeType.RECTANGLE) {
            binding.rectBtn.apply {
                background = resources.getDrawable(R.drawable.selected_shape)
            }
        } else {
            binding.rectBtn.apply {
                background = resources.getDrawable(R.drawable.unselected_shape)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}