//package kr.nanoit.config;
//
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import java.io.IOException;
//import java.security.InvalidAlgorithmParameterException;
//import java.security.InvalidKeyException;
//
//public class doPost {
//
//    String returnValue = null;
//
//    doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String returnVariable = null;
//        try {
//            if (request.getParameter("id") != null && request.getParameter("type") == null) {
//                String id = request.getParameter("id");
//                String password = request.getParameter("password");
//
//                if (SandBox.identifyMap.containsKey(id)) {
//                    Crypt crypt = new Crypt(enykey);
//                    crypt.cryptInit(SandBox.identifyMap.get(id).getEncryptKey());
//                    try {
//                        password = new String(crypt.deCrypt(password));
//                    } catch (InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException |
//                             BadPaddingException e) {
//                        e.printStackTrace();
//                    }
//
//                    if (password.equals(SandBox.identifyMap.get(id).getPassword())) {
//                        System.out.println(String.format("LOGIN REQUEST INBOUND, OLD TYPE, ID:%s PASSWORD:%s", id, password));
//                        OldServerInfo oldServerInfo = new OldServerInfo();
//                        oldServerInfo.setIp(SandBox.configuration.getString("tcp.server.connect"));
//                        oldServerInfo.setPort(SandBox.configuration.getString("tcp.server.port"));
//                        returnVariable = new XmlParser().write(oldServerInfo);
//                    } else {
//                        System.out.println(String.format("LOGIN REQUEST INBOUND, OLD TYPE, LOGIN FAIL, ID:%s PASSWORD:%s", id, password));
//                        OldServerInfo oldServerInfo = new OldServerInfo();
//                        oldServerInfo.setIp("LOGIN FAIL");
//                        oldServerInfo.setPort("LOGIN FAIL");
//                        returnVariable = new XmlParser().write(oldServerInfo);
//                    }
//                } else {
//                    System.out.println(String.format("LOGIN REQUEST INBOUND, OLD TYPE, LOGIN FAIL, ID:%s PASSWORD:%s", id, password));
//                    OldServerInfo oldServerInfo = new OldServerInfo();
//                    oldServerInfo.setIp("LOGIN FAIL");
//                    oldServerInfo.setPort("LOGIN FAIL");
//                    returnVariable = new XmlParser().write(oldServerInfo);
//                }
//            } else if (request.getParameter("id") != null && request.getParameter("type") != null) {
//                String type = request.getParameter("type");
//                String id = request.getParameter("id");
//                String password = request.getParameter("password");
//                System.out.println(String.format("LOGIN REQUEST INBOUND NEW TYPE:%s, ID:%s PASSWORD:%s", type, id, password));
//
//                ServerInfo1 serverInfo1 = new ServerInfo1();
//                serverInfo1.setIp(SandBox.configuration.getString("tcp.server.connect"));
//                serverInfo1.setPort(SandBox.configuration.getString("tcp.server.port"));
//
//                ServerInfo2 serverInfo2 = new ServerInfo2();
//                serverInfo2.setIp(SandBox.configuration.getString("tcp.server.connect"));
//                serverInfo2.setPort(SandBox.configuration.getString("tcp.server.port"));
//
//                ServerInfo serverInfo = new ServerInfo();
//                serverInfo.setServerInfo1(serverInfo1);
//                serverInfo.setServerInfo2(serverInfo2);
//
//                returnVariable = new XmlParser().write(serverInfo);
////            System.out.println(returnVariable);
//            }
//        } catch (Exception e) {
//            OldServerInfo oldServerInfo = new OldServerInfo();
//            oldServerInfo.setIp("LOGIN FAIL");
//            oldServerInfo.setPort("LOGIN FAIL");
//            returnVariable = new XmlParser().write(oldServerInfo);
//        }
//}
