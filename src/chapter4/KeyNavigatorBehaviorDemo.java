package chapter4;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class KeyNavigatorBehaviorDemo {

  public KeyNavigatorBehaviorDemo(){


    SimpleUniverse U=new SimpleUniverse();
    BranchGroup scene=createSceneGraph(U);
    U.addBranchGraph(scene);
  }

  private BranchGroup createSceneGraph(SimpleUniverse u){
    BranchGroup objRoot=new BranchGroup();
    TransformGroup vpTrans=null;
    TransformGroup tg=new TransformGroup();
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    tg.addChild(new ColorCube(0.05f));
    objRoot.addChild(tg);

    vpTrans=u.getViewingPlatform().getViewPlatformTransform();
    Transform3D translate=new Transform3D();
    translate.set(new Vector3f(0.0f,0.05f,0.0f));
    vpTrans.setTransform(translate);

    KeyNavigatorBehavior keyNavBeh=new KeyNavigatorBehavior(vpTrans);
    keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(),1000.0));

    MouseRotate myMouseRotate=new MouseRotate();
    myMouseRotate.setTransformGroup(tg);
    myMouseRotate.setSchedulingBounds(new BoundingSphere());

    objRoot.addChild(myMouseRotate);
    objRoot.addChild(keyNavBeh);
    objRoot.compile();
    return objRoot;
  }

  public static void main(String []args){
    KeyNavigatorBehaviorDemo knbd=new KeyNavigatorBehaviorDemo();
  }

}
