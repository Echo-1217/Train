//package com.example.Train.service.domainLayer.data;
//
//public enum NameKindTranslator {
//
//    A("諾亞方舟號", "A"), B("霍格華茲號", "B");
//
//
//    private String name;
//    private String kind;
//
//    NameKindTranslator(String name, String kind) {
//        this.name = name;
//        this.kind = kind;
//    }
//
//    public static String getName(String kind) {
//        for (NameKindTranslator n : NameKindTranslator.values()) {
//            if (n.kind.equals(kind)) {
//                return n.name;
//            }
//        }
//        return null;
//    }
//
//    public static String getKind(String name) {
//        for (NameKindTranslator n : NameKindTranslator.values()) {
//            if (n.name.equals(name)) {
//                return n.kind;
//            }
//        }
//        return null;
//    }
//
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getKind() {
//        return kind;
//    }
//
//    public void setKind(String kind) {
//        this.kind = kind;
//    }
//
//    @Override
//    public String toString() {
//        return super.toString();
//    }
//}