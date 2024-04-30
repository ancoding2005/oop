package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width,height;
    private String title;
    private long glfwWindow;

    private static Window window = null;

    private Window(){
        this.width = 960;
        this.height = 540;
        this.title = "Mario";
    }

    public static Window get(){
        if (Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }
    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();
    }

    public void init(){
        // thiết lập lệnh callback khi có lỗi
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW
        if (!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        //Cấu hình GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);

        glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);
        if (glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the glfwWindow");
        }

        // make openGL the context
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync
        glfwSwapInterval(1);
        // make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        }

     public void loop(){
        while (!glfwWindowShouldClose(glfwWindow)){
            glfwPollEvents();

            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
     }

}
