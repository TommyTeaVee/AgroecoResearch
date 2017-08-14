class button{
  int px;
  int py;
  int w;
  int h;
  String txt;
  boolean selected;
  
  button(int rPx, int rPy, int rW, int rH, String rTxt){
    px=rPx;
    py=rPy;
    w=rW;
    h=rH;
    txt=rTxt;
    selected=false;
  }
  
  boolean isMouseOver(int mx, int my){
    boolean ret=false;
    if(mx>=px && mx<=(px+w) && my>=py && my<=(py+h)){
      ret=true;
    }
    return ret;
  }
  
  void drawButton(){
    strokeWeight(2);
    if(selected){
      fill(76,175,80);
      stroke(76,175,80);
      rect(px,py,w,h);
      fill(255);
      text(txt,px+10,py+22);
    } else {
      noFill();
      stroke(76,175,80);
      rect(px,py,w,h);
      fill(76,175,80);
      text(txt,px+10,py+22);
    }
  }
}