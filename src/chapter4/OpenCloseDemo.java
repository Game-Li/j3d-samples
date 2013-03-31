package chapter4;

import javax.media.j3d.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

/*
 * NU
 */
public class OpenCloseDemo {

  public OpenCloseDemo(){
    SimpleUniverse U=new SimpleUniverse();
    BranchGroup scene=createSceneGraph();
    U.addBranchGraph(scene);
    U.getViewingPlatform().setNominalViewingTransform();
  }

  public BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();
    TransformGroup objRotate=new TransformGroup();
    objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objRotate.addChild(new ColorCube(0.4f));

    OpenBehavior openBehavior=new OpenBehavior(objRotate);
    openBehavior.setSchedulingBounds(new BoundingSphere());

    CloseBehavior closeBehavior=new CloseBehavior(objRotate);
    closeBehavior.setSchedulingBounds(new BoundingSphere());

    openBehavior.setBehaviorObjectPartner(closeBehavior);
    closeBehavior.setBehaviorObjectPartner(openBehavior);

    objRoot.addChild(objRotate);
    objRoot.addChild(closeBehavior);
    objRoot.addChild(openBehavior);
    return objRoot;
  }

  public static void main(String[] args) {
    OpenCloseDemo ocd=new OpenCloseDemo();
  }

}