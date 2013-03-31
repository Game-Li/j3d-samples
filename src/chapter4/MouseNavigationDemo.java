package chapter4;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.picking.behaviors.*;
import com.sun.j3d.utils.universe.*;


public class MouseNavigationDemo {

  public MouseNavigationDemo(){
    SimpleUniverse U=new SimpleUniverse();
    BranchGroup scene=createSceneGraph(U);
    scene.compile();
    U.addBranchGraph(scene);
  }

  private BranchGroup createSceneGraph(SimpleUniverse U){
    BranchGroup objRoot=new BranchGroup();
    TransformGroup objRotate=new TransformGroup();

    TransformGroup vpTrans=U.getViewingPlatform().getViewPlatformTransform();
    Canvas3D canvas3d=U.getCanvas();
    System.out.print(canvas3d);
    BoundingSphere bounds=new BoundingSphere(new Point3d(),1000.0);

    objRoot.addChild(objRotate);
    objRotate.setPickable(true);
    objRotate.addChild(new ColorCube(0.2f));
    objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
    PickRotateBehavior pickRotate=new PickRotateBehavior(objRoot,canvas3d,bounds);
    objRoot.addChild(pickRotate);	

    PickTranslateBehavior pickTranslate=new PickTranslateBehavior(objRoot,canvas3d,bounds);
    objRoot.addChild(pickTranslate);

    PickZoomBehavior pickZoom=new PickZoomBehavior(objRoot,canvas3d,bounds);
    objRoot.addChild(pickZoom);		

    KeyNavigatorBehavior keyNavBeh=new KeyNavigatorBehavior(vpTrans);
    keyNavBeh.setSchedulingBounds(new BoundingSphere(new Point3d(),1000.0));

    MouseRotate myMouseRotate=new MouseRotate();
    myMouseRotate.setTransformGroup(vpTrans);
    myMouseRotate.setFactor(0.0001);
    myMouseRotate.setSchedulingBounds(bounds);
    objRoot.addChild(myMouseRotate);

    MouseTranslate myMouseTranslate=new MouseTranslate();
    myMouseTranslate.setFactor(0.01);
    myMouseTranslate.setTransformGroup(vpTrans);
    myMouseTranslate.setSchedulingBounds(bounds);
    objRoot.addChild(myMouseTranslate);

    MouseZoom myMouseZoom=new MouseZoom();
    myMouseZoom.setFactor(0.01);
    myMouseZoom.setTransformGroup(vpTrans);
    myMouseZoom.setSchedulingBounds(bounds);
    objRoot.addChild(myMouseZoom);


    //CREATING THE BACKGROUND with an Image
    Toolkit toolkit=Toolkit.getDefaultToolkit();
    Image tmpImg=toolkit.getImage("./resources/moon.jpg");
    try{
      MediaTracker tracker=new MediaTracker(new Panel());
      tracker.addImage(tmpImg,0);
      tracker.waitForID(0);
    }catch(Exception e){e.printStackTrace();}

    BufferedImage textureImg=new BufferedImage(tmpImg.getWidth(null),tmpImg.getHeight(null),BufferedImage.TYPE_INT_RGB);

    Graphics graphics=textureImg.getGraphics();
    graphics.drawImage(tmpImg, 0, 0, null);
    graphics.dispose();
    ImageComponent2D image=new ImageComponent2D(ImageComponent2D.FORMAT_RGB,textureImg);
    Background bg=new Background(image);
    bg.setApplicationBounds(new BoundingSphere());

    objRoot.addChild(bg);		
    objRoot.addChild(keyNavBeh);
    return objRoot;
  }

  public static void main(String[] args) {
    MouseNavigationDemo mnd=new MouseNavigationDemo();
  }

}
