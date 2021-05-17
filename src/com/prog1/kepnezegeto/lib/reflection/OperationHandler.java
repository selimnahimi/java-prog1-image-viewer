package com.prog1.kepnezegeto.lib.reflection;

import com.prog1.kepnezegeto.lib.interfaces.IOperation;

/**
 * IOperation interfészt használó osztálykezelő osztály
 */
public class OperationHandler extends ClassHandler<IOperation> {
    public OperationHandler() {
        super(IOperation.class, "com.prog1.kepnezegeto.lib.operations");
    }
}
