package misc;

import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

public class NewStyleDemo extends JFrame{

  private static final Point3d USERPOSN = new Point3d(0,5,20);
  SimpleUniverse U=null;

  public NewStyleDemo(){
    GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas3d=new Canvas3D(config);
    this.add("Center",canvas3d);
    canvas3d.setFocusable(true);
    canvas3d.setVisible(true);
    U=new SimpleUniverse(canvas3d);
    BranchGroup scene=createSceneGraph();
    scene.compile();
    U.addBranchGraph(scene);
    initUserPosition(U);
    orbitControls(canvas3d);
    this.setVisible(true);
  }

  private BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();
    objRoot.addChild(new ColorCube(0.2f));
    objRoot.addChild(new CheckerFloor().getBG());
    addBackground(objRoot);
    return objRoot;
  }

  public static void main(String[] args) {
    NewStyleDemo nsd=new NewStyleDemo();

  }

  private void initUserPosition(SimpleUniverse su)
  // Set the user's initial viewpoint using lookAt()
  {
    ViewingPlatform vp = su.getViewingPlatform();
    TransformGroup steerTG = vp.getViewPlatformTransform();

    Transform3D t3d = new Transform3D();
    steerTG.getTransform(t3d);

    // args are: viewer posn, where looking, up direction
    t3d.lookAt( USERPOSN, new Point3d(0,0,0), new Vector3d(0,1,0));
    t3d.invert();

    steerTG.setTransform(t3d);
  }  // end of

  private void orbitControls(Canvas3D c)
  /* OrbitBehaviour allows the user to rotate around the scene, and to
	     zoom in and out.  */
  {
    OrbitBehavior orbit = 
        new OrbitBehavior(c, OrbitBehavior.REVERSE_ALL);
    orbit.setSchedulingBounds(new BoundingSphere());

    ViewingPlatform vp = U.getViewingPlatform();
    vp.setViewPlatformBehavior(orbit);	    
  }  // end of orbitControls()




  private void addBackground(BranchGroup objRoot)
  // A blue sky
  { Background back = new Background();
  back.setApplicationBounds( new BoundingSphere() );
  back.setColor(0.17f, 0.65f, 0.92f);    // sky colour
  objRoot.addChild( back );
  }  // end of addBackground()


}
