package chapter5;


import java.awt.GraphicsConfiguration;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import static java.lang.System.out;

public class LODDemo extends JFrame{

  private static final Point3d USERPOSN = new Point3d(0,5,20);
  SimpleUniverse U=null;

  public LODDemo(){
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    GraphicsConfiguration config=SimpleUniverse.getPreferredConfiguration();
    Canvas3D canvas3d=new Canvas3D(config);
    this.add("Center",canvas3d);
    canvas3d.setFocusable(true);
    canvas3d.setVisible(true);
    U=new SimpleUniverse(canvas3d);
    BranchGroup scene=createSceneGraph();
    lightScene(scene);

    scene.compile();
    U.addBranchGraph(scene);
    initUserPosition(U);
    orbitControls(canvas3d);
    this.setVisible(true);
  }

  private void setAppearance(Primitive s,Alpha alpha,BranchGroup scene){
    Color3f black=new Color3f(0.0f,0.0f,0.0f);
    Color3f blue=new Color3f(0.3f,0.3f,0.3f);
    Color3f specular=new Color3f(0.9f,0.9f,0.9f);

    Material blueMat=new Material(blue,black,blue,specular,25.0f);
    blueMat.setLightingEnable(true);
    Appearance blueApp=new Appearance();
    blueApp.setMaterial(blueMat);

    //ColorInterpolator colInt=new ColorInterpolator(alpha,blueMat,black,blue);
    //scene.addChild(colInt);


    s.setAppearance(blueApp);
  }

  private BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();

    //create target TG with capabilities.
    TransformGroup objMove=new TransformGroup();

    //create Distance LOD object
    Switch targetSwitch=new Switch();
    targetSwitch.setCapability(Switch.ALLOW_SWITCH_WRITE);

    //add visual objects to target switch
    Sphere s=new Sphere(.40f, 0, 25);
    setAppearance(s,null,objRoot);

    targetSwitch.addChild(s);
    targetSwitch.addChild(new Sphere(.40f, 0, 15));
    targetSwitch.addChild(new Sphere(.40f, 0, 10));
    targetSwitch.addChild(new Sphere(.40f, 0, 4));

    //create DistanceLOD object
    float[] distances={5.0f,10.0f,20.0f};
    DistanceLOD dLOD=new DistanceLOD(distances,new Point3f());
    dLOD.addSwitch(targetSwitch);
    dLOD.setSchedulingBounds(new BoundingSphere());

    //assemble the scene graph
    objMove.addChild(dLOD);
    objMove.addChild(targetSwitch);
    objRoot.addChild(objMove);

    objRoot.addChild(new CheckerFloor().getBG());
    addBackground(objRoot);

    return objRoot;
  }

  private void lightScene(BranchGroup scene)
  /* One ambient light, 2 directional lights */
  {
    Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

    // Set up the ambient light
    AmbientLight ambientLightNode = new AmbientLight(white);
    ambientLightNode.setInfluencingBounds(new BoundingSphere());
    scene.addChild(ambientLightNode);

    // Set up the directional lights
    Vector3f light1Direction  = new Vector3f(-1.0f, -1.0f, -1.0f);
    // left, down, backwards 
    Vector3f light2Direction  = new Vector3f(1.0f, -1.0f, 1.0f);
    // right, down, forwards

    DirectionalLight light1 = 
        new DirectionalLight(white, light1Direction);
    light1.setInfluencingBounds(new BoundingSphere());
    scene.addChild(light1);

    DirectionalLight light2 = 
        new DirectionalLight(white, light2Direction);
    light2.setInfluencingBounds(new BoundingSphere());
    scene.addChild(light2);
  }  // end of ligh

  public static void main(String[] args) {
    LODDemo nsd=new LODDemo();

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
