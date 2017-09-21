package com.aik.assist;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public class RelationTypeUtil {
    enum UserRelationEnum{
        // 0：互不关注 1：A关注B 2：B关注A 3：互相关注
        NONE_RELATION((byte) 0, "双方互不关注"),
        ARB_RELATION((byte) 1, "A关注B，B不关注A"),
        BRA_RELATION((byte) 2, "B关注A，A不关注B"),
        All_RELATION((byte) 3, "互相关注");
        private byte code;
        private String desc;

        UserRelationEnum(byte code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public byte getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static byte getABRelation(boolean isARB, boolean isBRA) {
        if (!isARB && !isBRA) {
            return UserRelationEnum.NONE_RELATION.getCode();
        } else if (isARB && !isBRA) {
            return UserRelationEnum.ARB_RELATION.getCode();
        } else if (!isARB && isBRA) {
            return UserRelationEnum.BRA_RELATION.getCode();
        } else {
            return UserRelationEnum.All_RELATION.getCode();
        }
    }
}
