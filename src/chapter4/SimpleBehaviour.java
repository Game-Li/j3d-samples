package chapter4;

import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import static java.lang.Math.*;

public class SimpleBehaviour extends Behavior{

  private TransformGroup tg;
  private Transform3D rotation=new Transform3D();
  private double angle=0.0;

  SimpleBehaviour(TransformGroup tg){
    this.tg=tg;
  }

  @Override
  public void initialize() {
    this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
  }

  @Override
  public void processStimulus(Enumeration arg0) {
    angle=(angle+0.1)%(PI);
    rotation.rotY(angle);
    tg.setTransform(rotation);
    this.wakeupOn(new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED));
  }

}