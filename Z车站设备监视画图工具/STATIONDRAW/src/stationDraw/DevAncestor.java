/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

/**
 *
 * <p>Title: 设备的类的祖先</p>
 * <p>Description: 该类是设备类的祖先，它有3个属性，坐标(x, y)，设备名称name</p>
 * @author zhanxiaoxin
 */
class DevAncestor {
    /**  设备的坐标(x, y)  */
    private int x,y;
    /** 设备的名称  */
    private String name;
    public DevAncestor(int m,int n,String name){
        x = m ;
        y = n ;
        this.name=name;
    }

    /**
     * 移动设备
     * @param x int
     * @param y int
     */
    public void move(int x,int y){
        this.x += x;
        this.y += y;
    }
    //移动对齐设备
    public void moveFulsh(int x,int y){
        this.x = x;
        this.y = y;
    }


    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getName(){
        return name;
    }

    public void setName(String newname){
        this.name = newname;
    }

}
