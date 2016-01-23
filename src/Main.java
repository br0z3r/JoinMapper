

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

//        InputStream stdin = null;
//        stdin = System.in;
//        FileInputStream stream = new FileInputStream("<filepath>/test");
//        System.setIn(stream);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        joinTwoFiles(bufferedReader);

    }

    private static void joinTwoFiles(BufferedReader bufferedReader)  throws IOException {

        if(!bufferedReader.ready() ) {
            return;
        }

        ArrayList<String>listQuery = new ArrayList<>();
        ArrayList<String>listUrl = new ArrayList<>();
        AbstractMap.SimpleEntry<String,String>tmpPair;


        String line;
        String currentKey = "";
        String tmpKey;
        String tmpValue;
        StringTokenizer stringTokenizer;

        while ((line = bufferedReader.readLine()) != null && line.length() != 0) {

            stringTokenizer = new StringTokenizer(line,"\t");
            tmpKey = stringTokenizer.nextToken();
            tmpValue = stringTokenizer.nextToken();

            if(tmpKey.isEmpty() || tmpValue.isEmpty()) {
                continue;
            }

            if(currentKey.isEmpty()) {
                currentKey = tmpKey;
            }

            if(currentKey.compareTo(tmpKey) != 0){
                printKeyUrlQuery(currentKey,listQuery,listUrl);
                currentKey = tmpKey;
                listUrl.clear();
                listQuery.clear();
            }

            tmpPair = parseValue(tmpValue);
            if(tmpPair == null) {
                continue;
            }

            switch (tmpPair.getKey()){
                case "url": {
                    listUrl.add(tmpPair.getValue());
                    break;
                }
                case "query": {
                    listQuery.add(tmpPair.getValue());
                    break;
                }
                default: {
                    break;
                }

            } //switch

        }
        printKeyUrlQuery(currentKey,listQuery,listUrl);
        listUrl.clear();
        listQuery.clear();

    }

    private static AbstractMap.SimpleEntry<String,String> parseValue(String value) {

        if(value == null || value.length() == 0) {
            return null;
        }
        AbstractMap.SimpleEntry<String,String>result;
        if(value.startsWith("url:")){
            result = new AbstractMap.SimpleEntry<>("url",value.substring(4));
            return result;

        }

        if(value.startsWith("query:")){
            result = new AbstractMap.SimpleEntry<>("query",value.substring(6));
            return result;

        }
        return null;

    }

    private static void printKeyUrlQuery(String key, ArrayList<String>listQuery, ArrayList<String>listUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder sbKeyQuery = new StringBuilder();
        String keyQuery;

        if(listQuery.isEmpty() || listUrl.isEmpty()){
            return;
        }

        for (String query:listQuery ) {
            keyQuery = sbKeyQuery.append(key).append("\t").append(query).append("\t").toString();
            for (String url:listUrl){
                System.out.println((stringBuilder.append(keyQuery).append(url).toString()));
                stringBuilder.setLength(0);
            }
            sbKeyQuery.setLength(0);
        }
    }

}



/*
user1	query:гугл
user1	url:google.ru
user2	query:стэпик
user2	query:стэпик курсы
user2	url:stepic.org
user2	url:stepic.org/explore/courses
user3	query:вконтакте

user1	гугл	google.ru
user2	стэпик	stepic.org
user2	стэпик	stepic.org/explore/courses
user2	стэпик курсы	stepic.org
user2	стэпик курсы	stepic.org/explore/courses


 */