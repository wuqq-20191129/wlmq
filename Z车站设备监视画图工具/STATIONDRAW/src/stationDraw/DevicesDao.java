/**
 * <p>Copyright: Copyright GuangZhou goldsign Co. 2005</p>
 * <p>Company: GuangZhou goldsign Co</p>
 */

package stationDraw;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import stationDraw.Util.ReadWriteXml;
import stationDraw.Vo.DbRecord;


/**
 * <p>Title: DevicesDao</p>
 * <p>Description: the class contains all the device data ,
 * text data and line data, image data. It is used to complete database operate</p>
 * @author zhanxiaoxin
 */
public class DevicesDao {
    ToolPanel toolpanel=new ToolPanel();
    /**  agm为包含单向闸机数组的对象，doubleAgm为包含双向闸机数组的对象  */
    AGMDev eg;
    AGMDev xeg;
    AGMDev twg;
    /**  bom为包含BOM数组的对象  */
    Device bom;
    /**  pca为包含PCA数组的对象。 */
    Device pca;
    /**  tvm为包含TVM数组的对象  */
    Device tvm;
    /**  itm为包含ITM数组的对象  */
    Device itm;
    /**  aqm为包含ITM数组的对象  */
    Device aqm;
    /**  xiantAr为包含线条数组的对象。 */
    public XiantArray lineAr;
    /**  img为包含图片数组的对象。 */
    public ImageArray img;
    /**  text为包含文本数组的对象  */
    public TextArray text;

    /** 执行SQL语句的statement */
    PreparedStatement stmt2;
    /** 用来存储从数据库中一次性取出设备信息的数组 */
    ArrayList devBuffer= new ArrayList();
  
    /**
     * 初始化界面上用到的各种对象。
     */
    public DevicesDao() {

        eg = new AGMDev(AppConstant.egImg, AppConstant.dev_type_eg, AppConstant.amgDirection );
        xeg = new AGMDev(AppConstant.xegImg, AppConstant.dev_type_xeg, AppConstant.amgDirection );
        twg = new AGMDev(AppConstant.twgImg, AppConstant.dev_type_twg , AppConstant.twgDirection);
        bom = new Device(AppConstant.bomImg, AppConstant.dev_type_bom);
        tvm = new Device(AppConstant.tvmImg, AppConstant.dev_type_tvm);
        pca = new Device(AppConstant.pcaImg, AppConstant.dev_type_pca);
        itm = new Device(AppConstant.itmImg, AppConstant.dev_type_itm);
        aqm = new Device(AppConstant.aqmImg, AppConstant.dev_type_aqm);

        img = new ImageArray();
        text = new TextArray();
        lineAr = new XiantArray();

    }

    /**
     * 建立连接，如果是连接关闭了，那就重新建立一个连接，否则就不改变
     * @throws Exception
     */
    public void setConnection() throws Exception{
        try{
            ReadWriteXml rwx=new ReadWriteXml("config.xml");

            if(AppConstant.con==null || AppConstant.con.isClosed()){
                Class.forName(rwx.driver);
                AppConstant.con = DriverManager.getConnection(rwx.url, rwx.userName, rwx.password);
            }
      
            if(AppConstant.con2==null || AppConstant.con2.isClosed()){
                Class.forName(rwx.driver);
                AppConstant.con2 = DriverManager.getConnection(rwx.url, rwx.userName2, rwx.password2);
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 关闭连接
     * @throws Exception
     */
    public void closeConnection() throws Exception{
        try {
            if(!AppConstant.con.isClosed())
            AppConstant.con.close();
        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 执行SQL条件查询语句，并返回ResultSet对象
     * @param condition String[] 代表数据库表中的各项，例如希望找出id等于2的记录，
     * 则condition中为"id" 而value为"2".
     * @param value String[] 代表数据库表中条件的值。
     * @throws Exception
     * @return ResultSet
     */
    public ResultSet exeQuery(String[] condition,String[] value) throws Exception{
        String sql="select * ";

        String part1=" from " + AppConstant.db_tab;
        sql=sql+part1;
        if(condition.length>0 && value.length == condition.length)
        {
            String part2 = " where " + condition[0] + "=? ";
            for (int i = 1; i < condition.length; i++)
                part2 = part2 + " AND " + condition[i] + "=? ";
            sql = sql + part2;

            stmt2 = AppConstant.con.prepareStatement(sql);
            for (int i = 0; i < value.length; i++) {
              stmt2.setString(i + 1, value[i]);
            }
            ResultSet rs = stmt2.executeQuery();
            return rs;
        }
        else
        {
            JOptionPane.showMessageDialog(null, "查询格式不正确");
            return null;
        }
    }

    /**
     * 根据setstmt2()设置好的insert语句的格式，插入记录。前十个数据为字符串格式的数据，后八个数据为整型数据。
     * @param values String[]
     * @param intvalues int[]
     * @throws Exception
     */
    public  void UpdateRecord(String[] values,int[] intvalues)throws Exception{
        try{
            for(int i=0; i<values.length; i++){
                stmt2.setString(i + 1, values[i]);
            }
            for(int i=0; i<intvalues.length; i++){
                stmt2.setInt(values.length + i + 1, intvalues[i]);
            }
            stmt2.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 设置好执行SQL语句的Statement:stmt2变量的格式
     * @throws SQLException
     */
    public void setStmt2() throws SQLException{
    String sql="insert into "+AppConstant.db_tab
        +" (node_id, line_id, station_id, dev_type_id, device_id, node_type,description,image_url "
            + ",image_direction ,fontsize,pos_x,pos_y ,start_x,start_y,end_x,end_y,ID_x ,ID_y)"
            +" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        stmt2=AppConstant.con.prepareStatement(sql);
    }

    /**
     * 保存各种设备的数据（闸机，BOM,TCM,TVM）
     * @param device Device 设备数组的引用。
     * @param idNum int 存储在数据库中的node_id的起始位置。
     * @param line String
     * @param station String
     * @throws Exception
     * @return int 返回保存的最后数据的位置+1
     */
    public int saveDev(Device device,int idNum,String line,String station) throws Exception{
        String[] str={"",line,station,"","","","","","",""};
        int[] va={0,0,0,0,0,0,0,0};

        for(int i=0; i<device.getArrayLength(); i++){

            //str[0]为对应数据库中的node_id，它由线路ID+车站ID+在车站里排列的号码
            str[0]=line+station+Integer.toString(idNum).substring(1);
            String[] values=device.getOneDevData(i);

            for(int n=0;n<6;n++)
                str[n+3]=values[n];

            va[0]=Integer.parseInt(values[7]);
            va[1]=Integer.parseInt(values[8]);
            va[6]=Integer.parseInt(values[9]);
            va[7]=Integer.parseInt(values[10]);

            UpdateRecord(str,va);
            idNum++;

            initializeStr(str);
            initializeInt(va);
            str[0]=line+station+Integer.toString(idNum).substring(1);
            str[1]=line;
            str[2]=station;
            str[3]=values[0];
            str[4]=values[1];
            str[5]=AppConstant.node_type_static_word;
            str[6]=values[6];
            va[0]=Integer.parseInt(values[9])+Integer.parseInt(values[7]);
            //把设备名文本的坐标的Y值减去一个固定的值,好让显示的时候没有偏移
            va[1]=Integer.parseInt(values[10])+Integer.parseInt(values[8])-12;
            UpdateRecord(str,va);   //向数据库里写入一条数据
            idNum++;
        }
        return idNum;
    }

    /**
     * 初始化字符串数组 str，把数组的所有字符串赋为空字符串
     * @param str String[]
     */
    public void initializeStr(String[] str){
        for (int i=0;i<str.length;i++)
            str[i]="";
    }

    /**
     * 初始化int数组 str，把数组的所有int变量赋为0
     * @param str int[]
     */
    public void initializeInt(int[] str){
        for(int i=0;i<str.length;i++)
            str[i]=0;
    }

    /**
     * 返回数据库中线路line_name, 车站station_name的车站中全部设备的数据(单和双向闸机,BOM,TVM,TCM)
     * 也就是5种设备的数据一次从数据库中取出。把数据库中分成两条记录（设备的位置和设备的名称）合并成一条记录。
     * @param line_name String
     * @param station_name String
     */
    public void getDbRecord(String line_name, String station_name) {
        try{
            String sql="select d1.device_id, d1.dev_type_id, d1.image_direction,"
                +" d2.description , d1.pos_x, d1.pos_y, d1.ID_x, d1.ID_y"
                +" from "+AppConstant.db_tab+" d1, "+AppConstant.db_tab +" d2 "
                +" where d1.line_id = d2.line_id AND d1.station_id = d2.station_id "
                +" AND d1.dev_type_id = d2.dev_type_id AND d1.device_id = d2.device_id "
                +" AND d1.line_id = ? AND d1.station_id = ? AND d1.node_type = ? AND d2.node_type = ?";
            stmt2 = AppConstant.con.prepareStatement(sql);
            stmt2.setString(1, line_name);
            stmt2.setString(2, station_name);
            stmt2.setString(3, "01");
            stmt2.setString(4, "03");
            ResultSet rs=stmt2.executeQuery();
            while (rs.next()){
                DbRecord record = new DbRecord(rs.getString("dev_type_id"),
                                    rs.getString("device_id"),
                                    rs.getString("description"),
                                    rs.getString("image_direction"),
                                    rs.getInt("pos_x"), rs.getInt("pos_y"),
                                    rs.getInt("ID_x"), rs.getInt("ID_y"));
                devBuffer.add(record);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从数据库中station_code中取出当前运行的所有线路和车站，并把存在一个ArrayList中，返回ArrayList
     * @throws Exception
     * @return ArrayList
     */
    public ArrayList getStation() throws Exception{
        ArrayList al = new ArrayList();
        String sql = "select line_id, station_id, chinese_name "
            +" from w_op_prm_station where record_flag='0' order by station_id";
        stmt2 = AppConstant.con2.prepareStatement(sql);
        ResultSet rs = stmt2.executeQuery();
        while(rs.next()){
            String str = rs.getString("line_id");
            str += rs.getString("station_id");
            str += rs.getString("chinese_name");
            al.add(str);
        }
        return al;
    }

    /**
     * 从数据库中取出所有的数据，然后把它放到声明好的对象数组里。
     * @param line_name String
     * @param station_name String
     * @throws Exception
     */
    public void openData(String line_name, String station_name) throws Exception {
        try {
            setConnection();
            getDbRecord(line_name, station_name);
            int x,y;

            String[] condition={"line_id","station_id","dev_type_id"};
            String[] value={line_name,station_name,"08"};

            for(int i=0; i<devBuffer.size(); i++){
                DbRecord rd=(DbRecord) devBuffer.get(i);
        
                //取单进站闸机数据资料(设备的坐标x,y，节点标识，设备名称，图象路径，设备名的x,y)
                if(rd.getType().equals(AppConstant.dev_type_eg)){
                    eg.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(),
                            rd.getDirection(),rd.getIDX(), rd.getIDY());
                }
                //取单出站闸机数据资料(设备的坐标x,y，节点标识，设备名称，图象路径，设备名的x,y)
                if(rd.getType().equals(AppConstant.dev_type_xeg)){
                    xeg.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(),
                            rd.getDirection(),rd.getIDX(), rd.getIDY());
                }

                //取双向闸机数据资料(设备的坐标x,y，节点标识，设备名称，图象路径，设备名的x,y)
                if(rd.getType().equals(AppConstant.dev_type_twg)){
                    twg.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(), rd.getDirection(),
                            rd.getIDX(), rd.getIDY());
                }

                //取BOM数据资料
                if(rd.getType().equals(AppConstant.dev_type_bom)){
                    bom.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(), rd.getDirection(),
                        rd.getIDX(), rd.getIDY());
                }

                //取TVM数据资料
                if(rd.getType().equals(AppConstant.dev_type_tvm)){
                    tvm.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(), rd.getDirection(),
                        rd.getIDX(), rd.getIDY());
                }

                //取ITM数据资料
                if(rd.getType().equals(AppConstant.dev_type_itm)){
                    itm.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(), rd.getDirection(),
                        rd.getIDX(), rd.getIDY());
                }

                //取TCM/PCA数据资料
                if(rd.getType().equals(AppConstant.dev_type_pca)){
                    pca.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(), rd.getDirection(),
                        rd.getIDX(), rd.getIDY());
                }

                //取AQM数据资料
                if(rd.getType().equals(AppConstant.dev_type_aqm)){
                    aqm.add(rd.getPosX(), rd.getPosY(), rd.getID(), rd.getName(), rd.getDirection(),
                        rd.getIDX(), rd.getIDY());
                }
            }

            //当取完数据后，devBuffer这个arraylist就没什么用了，为了可以让它回收，所以把它指向空
            devBuffer = null;

            //取出线条的两个端点的x,y值,node_type就不用取了，线条初始化的时候已经设定为04
            condition[2]="node_type";
            value[2] = AppConstant.node_type_line;
            new ToolPanel().lineSource.clear();
            new ToolPanel().xianName.clear();
            ResultSet rs = exeQuery(condition, value);
            Point p = new Point(0, 0);
            Point q = new Point(0, 0);
            while (rs.next()) {
                p.x = rs.getInt("start_x");
                p.y = rs.getInt("start_y");
                q.x = rs.getInt("end_x");
                q.y = rs.getInt("end_y");
                lineAr.add(p, q);
                lineAr.add(rs.getString("image_url"),rs.getString("description"));
            }

            //取图片数据
            value[2] = AppConstant.node_type_static_pic;
            rs = exeQuery(condition,value);
            while (rs.next())
            {
                x = rs.getInt("pos_x");
                y = rs.getInt("pos_y");
                String imgurl = rs.getString("image_url");
                img.addimage(imgurl, x, y);
            }

            //取文本数据
            String[] condition2={"line_id" ,"station_id" ,"node_type" ,"fontsize>" };
            String[] value2={line_name, station_name, AppConstant.node_type_static_word , "0"};
            rs = exeQuery(condition2, value2);
            while (rs.next())
            {
                String desc = rs.getString("description");
                String font = rs.getString("fontsize");
                x = rs.getInt("pos_x");
                //给文本加上文字的高度，使得在何的机器上，和我的机器上显示得一样
                y = rs.getInt("pos_y") + Integer.parseInt(font)*3 + 4;
                text.addText( desc, font, AppConstant.node_type_static_word, x, y );
            }

            //取动态文本
            value2[2] = AppConstant.node_type_dynamic_word;
            rs = exeQuery(condition2, value2);
            while(rs.next())
            {
                String desc = rs.getString("description");
                String font = rs.getString("fontsize");
                x = rs.getInt("pos_x");
                y = rs.getInt("pos_y")+ Integer.parseInt(font)*3 + 4;
                text.addText(desc, font , AppConstant.node_type_dynamic_word, x, y);
            }

            rs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 把对象数组里的数据保存到数据库里，对应着线路ID:line_name, 车站ID：station_name
     * @param line_name String
     * @param station_name String
     * @throws Exception
     */
    public void saveData(String line_name, String station_name) throws Exception{
        //把设备的数据保存到数据库中的初始化
        try {
            if(AppConstant.con.isClosed())
                setConnection();
            AppConstant.con.setAutoCommit(false);

            String sql = "delete from " + AppConstant.db_tab
                + " where station_id='" + station_name + "'"
                + " AND line_id='" + line_name + "'";
            //把原来数据库中的数据都删除掉，重新存入新的坐标数据
            stmt2 = AppConstant.con.prepareStatement(sql);
            stmt2.executeUpdate();
            //从线路和车站的号码确定数据库里的记录的node_id的起点
            int ID_num=10000;
            setStmt2();
            //保存进站闸机的数据
            ID_num=saveDev(eg, ID_num, line_name, station_name);
            //保存出站闸机的数据
            ID_num=saveDev(xeg, ID_num, line_name, station_name);
            //保存双向闸机的数据
            ID_num=saveDev(twg, ID_num, line_name, station_name);
            //保存BOM的数据
            ID_num=saveDev(bom, ID_num, line_name, station_name);
            //保存TVM的数据
            ID_num=saveDev(tvm, ID_num, line_name, station_name);
            //保存TCM/PCA的数据
            ID_num=saveDev(pca, ID_num, line_name, station_name);
            //保存ITM的数据
            ID_num=saveDev(itm, ID_num, line_name, station_name);
            //保存AQM的数据
            ID_num=saveDev(aqm, ID_num, line_name, station_name);
      
            //保存线条的数据
            String[] str={"",line_name,station_name,"","",AppConstant.node_type_line,"","","",""};
            int[] intValues={0,0,0,0,0,0,0,0};
            for (int i = 0; i < lineAr.getArrayLenth(); i++) {
                str[0] = line_name+station_name+Integer.toString(ID_num).substring(1);
                str[6]=lineAr.getXianName(i);//取字段description的值
                str[7]=lineAr.getLineImgUrl(i);//取字段ImageUrl的值
                System.out.println("str[7]="+str[7]);
                int[] values = lineAr.getOneXiantData(i);
                for (int n = 0; n < values.length; n++){
                    intValues[n] = values[n];
                }
                    UpdateRecord(str,intValues);
                    ID_num++;
            }

            //保存文本的数据
            initializeStr(str);
            initializeInt(intValues);
            str[1]=line_name;
            str[2]=station_name;
            for (int i = 0; i < text.getArrayLength(); i++) {
                str[0] = line_name + station_name + Integer.toString(ID_num).substring(1);
                String[] textValues=text.getOneTextData(i);
                for(int n=0;n<5; n++){
                    str[n+5]=textValues[n];
                }
                //intValues[0]代表记录中Pos_x的值
                intValues[0]=Integer.parseInt(textValues[5]);

                //intValues[1]代表记录中Pos_y的值，对Pos_y减去一定的值，因为在我的界面上文本是以左下角为基线，
                //而何建军的界面上要求文本的位置是以左上角为基线的，所以要把pos_y减去文本的高度，这样在显示的时候位置才不会偏差。
                intValues[1]=Integer.parseInt(textValues[6]) - Integer.parseInt(textValues[4])*3-4;
                UpdateRecord(str,intValues);
                ID_num++;
            }

            //保存图片数据
            initializeStr(str);
            initializeInt(intValues);
            str[1]=line_name;
            str[2]=station_name;
            str[5]=AppConstant.node_type_static_pic;
            for(int i = 0;i < img.getArrayLength(); i++){
                //str[0]对应与插入记录中的node_id的值
                str[0] = line_name + station_name + Integer.toString(ID_num).substring(1);
                String[] imgValues=img.getOneImageData(i);
                str[7]=imgValues[0];
                intValues[0]=Integer.parseInt(imgValues[1]);
                intValues[1]=Integer.parseInt(imgValues[2]);
                UpdateRecord(str,intValues);
                ID_num++;
            }

            AppConstant.con.commit();
            stmt2.close();
            AppConstant.con.setAutoCommit(true);
           // new ToolPanel().lineSource.clear();
           // new ToolPanel().xianName.clear();
        }
        catch (Exception e) {
            e.printStackTrace();
            AppConstant.con.rollback();
            closeConnection();
        }
    }

    /**
     * 改变设备的属性，例如设备的ID和设备名称
     * @param parent JFrame 该参数主要用于弹出对话框时，把对话框声明为parent的子窗口
     * @param source int 表示哪种设备的标志
     * @param i int 设备在数组中的序号
     */
    public void changename(JFrame parent, int source, int i) {
        Device one=findDev(source);
        if (one!=null)
            one.changeName(parent ,i);
    }

    /**
     * 根据标志source判断源设备是属于哪种类型(闸机，票务室,售票机,验票机)
     * @param source int
     * @return Device
     */
    public Device findDev(int source){
        Device one;
        if(source==AppConstant.source_button_eg) one=eg;
        else if(source==AppConstant.source_button_xeg) one=xeg;
        else if(source==AppConstant.source_button_twg) one=twg;
        else if(source==AppConstant.source_button_bom) one=bom;
        else if(source==AppConstant.source_button_tvm) one=tvm;
        else if(source==AppConstant.source_button_pca) one=pca;
        else if(source==AppConstant.source_button_itm) one=itm;
        else if(source==AppConstant.source_button_aqm) one=aqm;
        /*
        else if(source==90) one=bomNormal;
        else if(source==91) one=tvmNormal;
        else if(source==92) one=pcaNormal;
        else if(source==93) one=agmNormal;
        */
        else return null;
        return one;
   }

    /**
     * 根据送来的标志source, 往对应的设备数组里添加一个设备
     * @param source int 表示哪种设备的标志
     * @param x int
     * @param y int
     * @param dev_id String
     * @param name String
     * @param direction String
     * @return int
     */
    public int addDevice(int source, int x, int y, String dev_id , String name ,String direction){
        Device one=findDev(source);
        if (one!=null){
            int b = one.add(x, y, dev_id, name, direction);
            return b;
        }
        else return -1;
   }

    /**
     * 根据送来的标志source,移动对应的设备数组里序号为i的设备坐标
     * @param source int 表示哪种设备的标志
     * @param i int
     * @param x int
     * @param y int
     */
    public void move(int source,int i,int x,int y){
        Device one=findDev(source);
        if (one!=null)
        one.move(i,x,y);
    }
    //对齐操作，xx:参照物横坐标，yy:参照物纵坐标
    public void moveFush(int type,int i,String direction,int xx,int yy){
        Device one=findDev(type);
        if (one!=null)
        one.moveFulsh(i,direction,xx,yy);
    }

    /**
     * 根据送来的标志type,画确定数组里序号为i的设备
     * @param type int 表示哪种设备的标志
     * @param g Graphics
     * @param i int
     */
    public void drawOne(int type,Graphics g,int i){
        Device one=findDev(type);
        if (one!=null)
            one.displayOne(g, i);
    }

    /**
     * 根据送来的标志type,删除对应的设备数组序号为i的设备
     * @param type int 表示哪种设备的标志
     * @param i int
     */
    public void remove(int type,int i){
        Device one=findDev(type);
        if (one!=null)
            one.remove(i);
    }

    /**
     * 根据送来的标志type, 移动对应的设备数组序号为i的设备的名称
     * @param type int 表示哪种设备的标志
     * @param i int
     * @param x int
     * @param y int
     */
    public void moveName(int type,int i,int x,int y){
        Device one=findDev(type);
        if (one!=null)
            one.moveName(i,x,y);
    }
    //对齐操作，xx:参照物横坐标，yy:参照物纵坐标
    public void moveNameFush(int type,int i,String direction,int xx,int yy){
        Device one=findDev(type);
        if (one!=null)
            one.moveNameFulsh(i,xx,yy,direction);
    }

}
