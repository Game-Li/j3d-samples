package chapter2;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class ConeYoyoDemo {

  BranchGroup scene;

  public void createSceneGraph(){
    scene=new BranchGroup();
    BranchGroup bg1=new BranchGroup();

    TransformGroup tgRotate1;
    TransformGroup tgTranslate1;
    TransformGroup tgRotate2;
    TransformGroup tgTranslate2;

    Transform3D rotate=new Transform3D();
    Transform3D translate=new Transform3D();
    Appearance appearance=new Appearance();

    rotate.rotZ(Math.PI/2.0);
    translate.set(new Vector3f(0.2f,0.0f,0.0f));
    tgRotate1=new TransformGroup(rotate);
    tgTranslate1=new TransformGroup(translate);
    tgRotate1.addChild(tgTranslate1);
    Cone cone1=new Cone(0.4f,0.3f);
    cone1.setAppearance(appearance);
    tgTranslate1.addChild(cone1);

    rotate.rotZ(-Math.PI/2.0);
    translate.set(new Vector3f(-0.2f,0.0f,0.0f));
    tgRotate2=new TransformGroup(rotate);
    tgTranslate2=new TransformGroup(translate);
    tgRotate2.addChild(tgTranslate2);
    Cone cone2=new Cone(0.4f,0.3f);
    cone2.setAppearance(appearance);
    tgTranslate2.addChild(cone2);

    bg1.addChild(tgRotate1);
    bg1.addChild(tgRotate2);
    scene.addChild(bg1);

  }

  public ConeYoyoDemo(){
    SimpleUniverse U=new SimpleUniverse();
    U.getViewingPlatform().setNominalViewingTransform();
    createSceneGraph();
    this.scene.compile();
    U.addBranchGraph(scene);
  }



  public static void main(String[] args) {

    ConeYoyoDemo yoyo=new ConeYoyoDemo();
  }

}
