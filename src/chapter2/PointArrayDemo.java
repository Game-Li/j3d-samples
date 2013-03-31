package chapter2;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;

/*
 * PointArray is a subclass of GeometryArray which is a subclass of Geometry. 
 * Geometry is a node component i.e. it is not included in the scene graph.
 * Each Shape3D object has reference to a Appearance and a Geometry object.
 */

public class PointArrayDemo {

  public PointArrayDemo(){
    SimpleUniverse U=new SimpleUniverse();
    U.getViewingPlatform().setNominalViewingTransform();
    BranchGroup scene=createSceneGraph();
    scene.compile();
    U.addBranchGraph(scene);
  }

  private BranchGroup createSceneGraph(){
    BranchGroup objRoot=new BranchGroup();
    TransformGroup tgRotate=new TransformGroup();
    tgRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

    Transform3D rotate=new Transform3D();
    rotate.rotX(Math.PI/3);
    tgRotate.setTransform(rotate);


    Point3f[] points;
    Color3f[] colors;

    LineArray axisXLines=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
    points=new Point3f[]{new Point3f(1.0f,0.0f,0.0f),new Point3f(-1.0f,0.0f,0.0f)};
    colors=new Color3f[]{new Color3f(0,1,0),new Color3f(0,0,1)};
    axisXLines.setCoordinates(0,points);
    axisXLines.setColors(0,colors);

    LineArray axisYLines=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
    points=new Point3f[]{new Point3f(0.0f,1.0f,0.0f),new Point3f(0.0f,-1.0f,0.0f)};
    colors=new Color3f[]{new Color3f(0,1,0),new Color3f(0,0,1)};
    axisYLines.setCoordinates(0,points);
    axisYLines.setColors(0,colors);

    LineArray axisZLines=new LineArray(2,LineArray.COORDINATES|LineArray.COLOR_3);
    points=new Point3f[]{new Point3f(0.0f,0.0f,1.0f),new Point3f(0.0f,0.0f,-1.0f)};
    colors=new Color3f[]{new Color3f(0,1,0),new Color3f(0,0,1)};
    axisZLines.setCoordinates(0,points);
    axisZLines.setColors(0,colors);

    Alpha alpha=new Alpha(-1,4000);
    RotationInterpolator rotator=new RotationInterpolator(alpha,tgRotate);
    BoundingSphere bounds=new BoundingSphere();
    rotator.setSchedulingBounds(bounds);

    tgRotate.addChild(new ColorCube(0.2f));
    tgRotate.addChild(rotator);
    tgRotate.addChild(new Shape3D(axisXLines));
    tgRotate.addChild(new Shape3D(axisYLines));
    tgRotate.addChild(new Shape3D(axisZLines));


    objRoot.addChild(tgRotate);
    return objRoot;
  }

  public static void main(String ...args){
    PointArrayDemo pad=new PointArrayDemo();
  }
}