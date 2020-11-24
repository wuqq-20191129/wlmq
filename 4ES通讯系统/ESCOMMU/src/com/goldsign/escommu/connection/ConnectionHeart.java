package com.goldsign.escommu.connection;

public class ConnectionHeart implements Runnable{

    private CommuConnection commuConnection;

    public ConnectionHeart(CommuConnection commuConnection) {
        this.commuConnection = commuConnection;
    }

    @Override
    public void run() {
        try{
            while(true){
                commuConnection.sendTestData();

                Thread.sleep(60000);
            }  
        }catch(Exception e){
            commuConnection.closeConnection();
        }
    }

}
