package chapter4;

import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.*;

public class CloseBehavior extends Behavior{

  private TransformGroup targetTG;
  private WakeupCriterion pairPostCondition;
  private WakeupCriterion AWTEventCondition;

  private Transform3D rotation=new Transform3D();
  private double angle;

  public CloseBehavior(TransformGroup targetTG){
    this.targetTG=targetTG;
    AWTEventCondition=new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);
  }

  public void setBehaviorObjectPartner(Behavior behaviorObject){
    pairPostCondition=new WakeupOnBehaviorPost(behaviorObject,1);
  }

  @Override
  public void initialize() {
    this.wakeupOn(pairPostCondition);

  }

  @Override
  public void processStimulus(Enumeration criteria) {
    if(AWTEventCondition.hasTriggered()){
      angle=(angle+0.1)%Math.PI;
      rotation.rotY(angle);
      targetTG.setTransform(rotation);
      //this.wakeupOn(pairPostCondition);
      //postId(1);
    }
    else{
      this.wakeupOn(AWTEventCondition);
    }

  }

}
