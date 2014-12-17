package sharedData;
//用户登陆信息类
public class user {
	public int id;
	public String userName;
	public String userPw;
	public int playerId;
	public int islogin;
	public String toMyString()
	{
		String s=id+" "+userName+" "+userPw+" "+playerId+" "+islogin;
		return s;
	}
	public int check(String id,String pw)
	{
		if(id.equals(userName)&&pw.equals(userPw))return 1;
		else return 0;
	}
}
