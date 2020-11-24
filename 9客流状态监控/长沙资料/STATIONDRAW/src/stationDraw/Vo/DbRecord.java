/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw.Vo;
/**
 * 这个类作为从数据库中取出一条记录, 然后存储在这个类的一个对象里
 * 它的属性有设备的类型,设备的ID,设备的方向, 设备的名称, 设备的位置坐标, 设备的名称的坐标
 * @author zhanxiaoxin
 * @version 1.0
 */
public class DbRecord
{
 /**  type表示设备的类型ID:dev_type_id, id表示设备的ID:device_id,
  *  direction表示设备的方向代码:image_diretion , name表示设备的名称 */
 private String type, id, direction, name;
 private int pos_x, pos_y, idx, idy;

  public DbRecord(String dev_type, String dev_id, String name, String img_direction,
                  int pos_x, int pos_y , int idx, int idy )
  {
    type = dev_type;
    id = dev_id.trim();
    direction = img_direction;
    this.name=name.trim();
    this.pos_x = pos_x;
    this.pos_y = pos_y;
    this.idx = idx;
    this.idy = idy;
      }

  public String getType()
  {
    return type;
  }

  public String getID()
  {
   return id;
  }

 public String getDirection()
 {
   return direction;
 }

 public String getName()
 {
   return name;
 }

 public int getPosX()
 {
   return pos_x;
 }

 public int getPosY()
 {
   return pos_y;
 }

 public int getIDX()
 {
   return idx;
 }

 public int getIDY()
 {
    return idy;
  }
}
