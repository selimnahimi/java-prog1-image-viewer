package com.prog1.kepnezegeto.lib;

/**
 * IOperation interfészt használó osztálykezelő osztály
 */
public class OperationHandler extends ClassHandler<IOperation> {
    public OperationHandler() {
        super(IOperation.class, "com.prog1.kepnezegeto.lib.operations");
    }
}
