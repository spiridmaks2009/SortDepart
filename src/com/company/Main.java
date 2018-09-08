package com.company;

import javax.xml.bind.Element;
import java.util.*;

public class Main {
    
    public static List<Elem> listTemp = new ArrayList<Elem>();
    public static List<Elem> result = new ArrayList<Elem>();

    public static void main(String[] args) {
        // write your code here
        String[] depart = {"K2\\SK1\\SSK2", "K1\\SK1\\SSK1", "K2", "K1\\SK2", "K1\\SK1\\SSK2", "K2\\SK1\\SSK1", "K1\\SK1"};

        System.out.println("Исходные данные: ");
        for (String str : depart) {
            System.out.println(str);
            Elem el = new Elem(str, null);
            listTemp.add(el);
        }

        for (String str : depart) {
            StringTokenizer st = new StringTokenizer(str, "\\");
            if (st.countTokens() > 1) {
                String parentName = "";
                for (int i = 0; i < st.countTokens(); i++) {
                    parentName += st.nextToken() + "\\";
                }

                parentName = parentName.substring(0, parentName.length() - 1);

                Elem parent = findParent(parentName);
                for (Elem el : listTemp) {
                    if (el.depName.equals(str))
                        el.parent = parent;
                }
            }
        }

        List<Elem> firstElem = new ArrayList<Elem>();
        for (Elem elem : listTemp)
            if (elem.parent == null) firstElem.add(elem);

        Collections.sort(firstElem, new Comparator<Elem>() {
            @Override
            public int compare(Elem o1, Elem o2) {

                return o1.depName.compareTo(o2.depName);
            }
        });

        for (Elem elem : firstElem) {
            sortAsc(elem);
        }

        System.out.println("-----------------------------");
        System.out.println("Сортировка по возрастанию: ");

        for (Elem elem : result) {
            try {
                System.out.println(elem.depName);
            } catch (Exception ex) {}
        }

        Collections.sort(firstElem, new Comparator<Elem>() {
            @Override
            public int compare(Elem o1, Elem o2) {

                return o2.depName.compareTo(o1.depName);
            }
        });

        result.removeAll(result);
        for (Elem elem : firstElem) {
            sortDesc(elem);
        }

        System.out.println("-----------------------------");
        System.out.println("Сортировка по убыванию: ");

        for (Elem elem : result) {
            try {
                System.out.println(elem.depName);
            } catch (Exception ex) {}
        }


    }

    public static void sortAsc(Elem parent) {

        result.add(parent);
        List<Elem> childs = new ArrayList<Elem>();
        for (Elem elem : listTemp) {
            if(elem.parent == parent)
                childs.add(elem);
        }

        Collections.sort(childs, new Comparator<Elem>() {
            @Override
            public int compare(Elem o1, Elem o2) {

                return o1.depName.compareTo(o2.depName);
            }
        });

        for (Elem elem : childs) {
                sortAsc(elem);
        }

    }

    public static void sortDesc(Elem parent) {

        result.add(parent);
        List<Elem> childs = new ArrayList<Elem>();
        for (Elem elem : listTemp) {
            if (elem.parent == parent)
                childs.add(elem);
        }

        Collections.sort(childs, new Comparator<Elem>() {
            @Override
            public int compare(Elem o1, Elem o2) {

                return o2.depName.compareTo(o1.depName);
            }
        });

        for (Elem elem : childs) {
            sortDesc(elem);
        }
    }

    public static Elem findParent(String parentName) {

        boolean isParentFounded = false;
        for(Elem elem : listTemp) {
            if(elem.depName.equals(parentName)) {
                isParentFounded = true;
                return elem;
            }
        }
        if(!isParentFounded) {
            Elem parent = new Elem(parentName,null);
            StringTokenizer st = new StringTokenizer(parentName,"\\");
            if(st.countTokens() > 1) {
                String name = "";
                for (int i = 0; i < st.countTokens()-1; i++) {
                    name += st.nextToken();
                }
                parent.parent = findParent(name);
            }
            listTemp.add(parent);
            return parent;
        }
        return null;
    }

    public static class Elem{
        String depName;
        Elem parent;

        public Elem(String string, Elem parent) {
            this.depName = string;
            this.parent = parent;
        }
    }
}
