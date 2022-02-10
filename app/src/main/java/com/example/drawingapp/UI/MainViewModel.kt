package com.example.drawingapp.UI

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.drawingapp.Enums.SelectedColor
import com.example.drawingapp.Enums.ShapeType

class MainViewModel: ViewModel() {
  private  var selectedColor=MutableLiveData<SelectedColor>()
   private var selectedShape=MutableLiveData<ShapeType>(ShapeType.SMOOTHLINE)
    fun setColorSelected(color: SelectedColor){
        selectedColor.postValue(color)
    }
    fun setShapeSelected(shapeType: ShapeType){
        selectedShape.postValue(shapeType)
    }

    fun getSelectedColor()=selectedColor
    fun getSelectedShape()=selectedShape
}