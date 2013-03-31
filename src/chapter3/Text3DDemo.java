package chapter3;

import java.awt.Font;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class Text3DDemo {

  public Text3DDemo(){
    SimpleUniverse U=new SimpleUniverse();
    BranchGroup scene=createSceneGraph();
    scene.compile();
    U.addBranchGraph(scene);
    U.getViewingPlatform().setNominalViewingTransform();
  }

  public BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();
    TransformGroup tgSpin =new TransformGroup();
    tgSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    Font3D font3d=new Font3D(new Font("Helvetica",Font.PLAIN,1),new FontExtrusion());
    Text3D textGeom=new Text3D(font3d,new String("Java 3D"),new Point3f(0.0f,-1.0f,-5.0f),Text3D.ALIGN_CENTER,Text3D.PATH_DOWN);
    Shape3D textShape=new Shape3D(textGeom);

    Alpha alpha=new Alpha(-1,4000);
    RotationInterpolator rotator=new RotationInterpolator(alpha,tgSpin);
    BoundingSphere bounds=new BoundingSphere();
    rotator.setSchedulingBounds(bounds);
    tgSpin.addChild(rotator);

    tgSpin.addChild(textShape);
    objRoot.addChild(tgSpin);
    return objRoot;
  }

  public static void main(String[] args) {
    Text3DDemo t3ddemo=new Text3DDemo();
  }
}