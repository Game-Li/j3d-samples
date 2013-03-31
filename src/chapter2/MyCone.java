package chapter2;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class MyCone {

  BranchGroup scene;

  public void createSceneGraph(){
    scene=new BranchGroup();
    BranchGroup bg1=new BranchGroup();

    TransformGroup tgRotate1;
    TransformGroup tgTranslate1;

    Transform3D rotate=new Transform3D();
    Transform3D translate=new Transform3D();
    Appearance appearance=new Appearance();
    appearance.setColoringAttributes(new ColoringAttributes());

    rotate.rotZ(Math.PI/2.0);
    translate.set(new Vector3f(0.2f,0.0f,0.0f));
    tgRotate1=new TransformGroup(rotate);
    tgTranslate1=new TransformGroup(translate);
    tgRotate1.addChild(tgTranslate1);
    Cone cone1=new Cone(0.4f,0.3f);
    cone1.setAppearance(appearance);
    tgTranslate1.addChild(cone1);

    bg1.addChild(tgRotate1);
    scene.addChild(bg1);

  }

  public MyCone(){
    SimpleUniverse U=new SimpleUniverse();
    U.getViewingPlatform().setNominalViewingTransform();
    createSceneGraph();
    this.scene.compile();
    U.addBranchGraph(scene);
  }

  public static void main(String[] args) {
    MyCone c=new MyCone();
  }
}