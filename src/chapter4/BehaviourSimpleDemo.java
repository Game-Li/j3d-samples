package chapter4;

import javax.media.j3d.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class BehaviourSimpleDemo {

  public BehaviourSimpleDemo(){
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

    SimpleBehaviour behv=new SimpleBehaviour(objRotate);
    behv.setSchedulingBounds(new BoundingSphere());

    objRoot.addChild(objRotate);
    objRoot.addChild(behv);
    return objRoot;
  }

  public static void main(String args[]){
    BehaviourSimpleDemo bsd=new BehaviourSimpleDemo();
  }

}
