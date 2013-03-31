package chapter1;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class TransformGroupDemo {

  public BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();

    Transform3D rotatex=new Transform3D();
    rotatex.rotX(Math.PI/4);

    Transform3D rotatey=new Transform3D();
    rotatey.rotY(Math.PI/4);
    rotatex.mul(rotatey);

    TransformGroup objRotate=new TransformGroup(rotatex);
    objRotate.addChild(new ColorCube(0.2f));

    objRoot.addChild(objRotate);

    return objRoot;
  }

  public TransformGroupDemo(){
    SimpleUniverse U=new SimpleUniverse();
    U.getViewingPlatform().setNominalViewingTransform();
    BranchGroup scene=createSceneGraph();
    scene.compile();
    U.addBranchGraph(scene);
  }

  public static void main(String ...args){
    TransformGroupDemo tgd=new TransformGroupDemo();

  }
}