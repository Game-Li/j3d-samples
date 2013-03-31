package chapter5;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import static java.lang.System.out;

public class AnimationDemo extends JFrame{

  private static final Point3d USERPOSN = new Point3d(0,5,20);
  SimpleUniverse U=null;

  public AnimationDemo(){
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

    //GraphicsEnvironment env=GraphicsEnvironment.getLocalGraphicsEnvironment();
    //GraphicsDevice dev=env.getDefaultScreenDevice();
    //dev.setFullScreenWindow(this);

  }

  private void setAppearance(Primitive s,Alpha alpha,BranchGroup scene){
    Color3f black=new Color3f(0.0f,0.0f,0.0f);
    Color3f blue=new Color3f(0.3f,0.3f,0.3f);
    Color3f specular=new Color3f(0.9f,0.9f,0.9f);

    Material blueMat=new Material(blue,black,blue,specular,25.0f);
    blueMat.setLightingEnable(true);
    Appearance blueApp=new Appearance();
    blueApp.setMaterial(blueMat);

    ColorInterpolator colInt=new ColorInterpolator(alpha,blueMat,black,blue);
    scene.addChild(colInt);


    s.setAppearance(blueApp);
  }

  private BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();

    TransformGroup objSpin=new TransformGroup();
    objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    Alpha alpha=new Alpha(-1,6000);

    //ROTATION INTERPOLATOR
    //RotationInterpolator rotInt=new RotationInterpolator(alpha,objSpin);
    //rotInt.setSchedulingBounds(new BoundingSphere());

    //POSITION INTERPOLATOR
    Transform3D transform3d=new Transform3D();
    transform3d.rotY(Math.PI/4);
    //transform3d.rotZ(Math.PI/4);
    //PositionInterpolator posInt=new PositionInterpolator(alpha,objSpin,transform3d,0.0f,2.0f);
    //posInt.setSchedulingBounds(new BoundingSphere());

    //SCALE INTERPOLATOR
    //ScaleInterpolator scaleInt=new ScaleInterpolator(alpha,objSpin,transform3d,0.2f,0.9f);
    //scaleInt.setSchedulingBounds(new BoundingSphere());

    //ROTPOSPATHINTERPOLATOR
    Transform3D axisOfRotPos=new Transform3D();
    float[] knots=new float[]{0.0f,0.3f,0.6f,1.0f};
    Quat4f []quats=new Quat4f[4];
    Point3f []positions=new Point3f[4];

    AxisAngle4f axis=new AxisAngle4f(1.0f,0.0f,0.0f,0.0f);
    axisOfRotPos.set(axis);
    quats[0]=new Quat4f(0.0f,1.0f,1.0f,0.0f);
    quats[1]=new Quat4f(1.0f,0.0f,0.0f,0.0f);
    quats[2]=new Quat4f(0.0f,1.0f,0.0f,0.0f);
    quats[3]=new Quat4f(0.0f,0.0f,1.0f,0.0f);
    positions[0]=new Point3f(0.0f,0.0f,-1.0f);
    positions[1]=new Point3f(1.0f,-1.0f,-2.0f);
    positions[2]=new Point3f(-1.0f,1.0f,-3.0f);
    positions[3]=new Point3f(0.0f,1.0f,-3.0f);

    out.println(quats);
    out.println(positions);

    RotPosPathInterpolator rotPosPath=new RotPosPathInterpolator(alpha,objSpin,axisOfRotPos,knots,quats,positions);
    rotPosPath.setSchedulingBounds(new BoundingSphere());

    Sphere s=new Sphere();
    setAppearance(s,alpha,objRoot);

    objSpin.addChild(s);

    objRoot.addChild(objSpin);
    //objRoot.addChild(rotInt);
    //objRoot.addChild(posInt);
    //objRoot.addChild(scaleInt);
    objRoot.addChild(rotPosPath);
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
    ambientLightNode.setInfluencingBounds(new BoundingSphere(new Point3d(),5.0f));
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
    AnimationDemo nsd=new AnimationDemo();


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
