package chapter1;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class AnimationDemo1 {

  public BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();

    Transform3D rotatex=new Transform3D();
    rotatex.rotX(Math.PI/3);

    Transform3D rotatey=new Transform3D();
    rotatey.rotY(Math.PI/3);
    rotatex.mul(rotatey);

    TransformGroup objRotate=new TransformGroup(rotatex);
    TransformGroup objSpin=new TransformGroup();
    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objRotate.addChild(objSpin);
    objSpin.addChild(new ColorCube(0.2f));

    Alpha rotationalpha=new Alpha(-1,4000);
    RotationInterpolator rotator=new RotationInterpolator(rotationalpha,objSpin);
    BoundingSphere bound=new BoundingSphere(new Point3d(0,0,0),0.5);
    rotator.setSchedulingBounds(bound);
    objSpin.addChild(rotator);

    objRoot.addChild(objRotate);
    return objRoot;
  }

  public AnimationDemo1(){
    SimpleUniverse U=new SimpleUniverse();
    U.getViewingPlatform().setNominalViewingTransform();
    BranchGroup scene=createSceneGraph();
    scene.compile();
    U.addBranchGraph(scene);
  }

  public static void main(String ...args){
    AnimationDemo1 ad1=new AnimationDemo1();

  }
}