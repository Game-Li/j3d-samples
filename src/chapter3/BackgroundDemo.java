package chapter3;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;

public class BackgroundDemo {
	
	public BackgroundDemo(){
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
		Text3D textGeom=new Text3D(font3d,new String("Java 3D"),new Point3f(0.0f,-0.0f,-5f));
		Shape3D textShape=new Shape3D(textGeom);
		
		Alpha alpha=new Alpha(-1,4000);
		RotationInterpolator rotator=new RotationInterpolator(alpha,tgSpin);
		BoundingSphere bounds=new BoundingSphere();
		rotator.setSchedulingBounds(bounds);
		tgSpin.addChild(rotator);
		
		//CREATING THE BACKGROUND with a Color
		//Background bg=new Background(1.0f,0.0f,1.0f);
		//bg.setApplicationBounds(new BoundingSphere());
		
		//CREATING THE BACKGROUND with an Image
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Image tmpImg=toolkit.getImage("moon.jpg");
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
		
		tgSpin.addChild(textShape);
		objRoot.addChild(tgSpin);
		objRoot.addChild(bg);
		return objRoot;
	}

	public static void main(String[] args) {
		BackgroundDemo bgdemo=new BackgroundDemo();
	}

}
