package chapter1;

import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;


public class HelloJava3D{


  public HelloJava3D(){

    //Canvas3D canvas3d=new Canvas3D(null);
    BranchGroup scene=new BranchGroup();
    scene.addChild(new ColorCube(0.2f));
    scene.compile();

    SimpleUniverse U=new SimpleUniverse();
    U.getViewingPlatform().setNominalViewingTransform();
    U.addBranchGraph(scene);

  }




  public static void main(String ...args){
    HelloJava3D j3d=new HelloJava3D();
  }

}
