/* @pjs font="calibril.ttf"; */

PFont font;
button crop1;
button crop2;
button intercroppingY;
button intercroppingN;
button pestControlY;
button pestControlN;
button soilConservY;
button soilConservN;
button drawField;

String fieldName;

int state=0;

void setup(){
  size(800,500);
  
  //font = loadFont("Calibri-22.vlw");
  //textFont(font);
  font=loadFont("calibril.ttf");
  textFont(font,22);
  
  createButtons();
  
  fieldName=getFieldName();
}

void draw(){
  background(255);
  noFill();
  strokeWeight(4);
  stroke(76,175,80);
  rect(0,0,width,height);
  fill(76,175,80);
  text(fieldName,50,30);
  if(state==0){
    drawButtons();
  }
}

void mouseClicked(){
  if(crop1.isMouseOver(mouseX,mouseY)){
    crop1.selected=!crop1.selected;
    crop2.selected=!crop1.selected;
  } else if(crop2.isMouseOver(mouseX,mouseY)){
    crop2.selected=!crop2.selected;
    crop1.selected=!crop2.selected;
  } else if(intercroppingY.isMouseOver(mouseX,mouseY)){
    intercroppingY.selected=!intercroppingY.selected;
    intercroppingN.selected=!intercroppingY.selected;
  } else if(intercroppingN.isMouseOver(mouseX,mouseY)){
    intercroppingN.selected=!intercroppingN.selected;
    intercroppingY.selected=!intercroppingN.selected;
  } else if(pestControlY.isMouseOver(mouseX,mouseY)){
    pestControlY.selected=!pestControlY.selected;
    pestControlN.selected=!pestControlY.selected;
  } else if(pestControlN.isMouseOver(mouseX,mouseY)){
    pestControlN.selected=!pestControlN.selected;
    pestControlY.selected=!pestControlN.selected;
  } else if(soilConservY.isMouseOver(mouseX,mouseY)){
    soilConservY.selected=!soilConservY.selected;
    soilConservN.selected=!soilConservY.selected;
  } else if(soilConservN.isMouseOver(mouseX,mouseY)){
    soilConservN.selected=!soilConservN.selected;
    soilConservY.selected=!soilConservN.selected;
  } else if(drawField.isMouseOver(mouseX,mouseY)){
    calculateField();
    state=1;
  }
}

void createButtons(){
  crop1 = new button(50,100,(int)textWidth("1 Crop")+20,30,"1 Crop");
  crop2 = new button(300,100,(int)textWidth("2 Crops")+20,30,"2 Crops");
  intercroppingY = new button(50,170,(int)textWidth("Intercropping")+20,30,"Intercropping");
  intercroppingN = new button(300,170,(int)textWidth("No intercropping")+20,30,"No intercropping");
  pestControlY = new button(50,240,(int)textWidth("Pest control")+20,30,"Pest control");
  pestControlN = new button(300,240,(int)textWidth("No pest control")+20,30,"No pest control");
  soilConservY = new button(50,310,(int)textWidth("Soil conservation")+20,30,"Soil conservation");
  soilConservN = new button(300,310,(int)textWidth("No soil conservation")+20,30,"No soil conservation");
  drawField = new button(50,430,(int)textWidth("DRAW FIELD")+20,30,"DRAW FIELD");
}

void drawButtons(){
  crop1.drawButton();
  crop2.drawButton();
  intercroppingY.drawButton();
  intercroppingN.drawButton();
  pestControlY.drawButton();
  pestControlN.drawButton();
  soilConservY.drawButton();
  soilConservN.drawButton();
  drawField.drawButton();
}

void calculateField(){
}