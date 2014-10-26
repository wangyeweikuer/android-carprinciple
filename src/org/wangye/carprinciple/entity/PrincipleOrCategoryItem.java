/**
 * Copyright (C) 2014 Baidu, Inc. All Rights Reserved.
 */
package org.wangye.carprinciple.entity;

/**
 *
 * @author wangye04
 * @datetime Oct 26, 2014 11:45:54 AM
 */
public class PrincipleOrCategoryItem {
    private boolean isPrincipleNotCategory;
    private Principle principle;
    private String category;
    private String displayText;

    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == this.getClass()) {
            PrincipleOrCategoryItem po = (PrincipleOrCategoryItem) o;
            if (isPrincipleNotCategory == po.isPrincipleNotCategory) {
                if (isPrincipleNotCategory == true) {
                    return principle.equals(po.principle);
                } else {
                    return category.equals(po.category);
                }
            }
        }
        return false;
    }

    public static PrincipleOrCategoryItem newPrinciple(Principle principle, String displayText) {
        PrincipleOrCategoryItem pc = new PrincipleOrCategoryItem();
        pc.setPrinciple(principle);
        pc.setDisplayText(displayText);
        pc.setPrincipleNotCategory(true);
        return pc;
    }

    public static PrincipleOrCategoryItem newCategory(String category, String displayText) {
        PrincipleOrCategoryItem pc = new PrincipleOrCategoryItem();
        pc.setCategory(category);
        pc.setDisplayText(displayText);
        pc.setPrincipleNotCategory(false);
        return pc;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public boolean isPrincipleNotCategory() {
        return isPrincipleNotCategory;
    }

    public void setPrincipleNotCategory(boolean isPrincipleNotCategory) {
        this.isPrincipleNotCategory = isPrincipleNotCategory;
    }

    public Principle getPrinciple() {
        return principle;
    }

    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
