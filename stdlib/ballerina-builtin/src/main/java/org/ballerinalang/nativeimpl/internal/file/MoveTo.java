/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.nativeimpl.internal.file;

import org.apache.commons.io.FileUtils;
import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BLangVMErrors;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.nativeimpl.internal.Constants;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.natives.annotations.ReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Move file/directory to another directory.
 *
 * @since 0.971.0
 */
@BallerinaFunction(
        orgName = Constants.ORG_NAME,
        packageName = Constants.PACKAGE_NAME,
        functionName = "moveTo",
        receiver = @Receiver(type = TypeKind.OBJECT, structType = Constants.PATH_STRUCT,
                             structPackage = Constants.PACKAGE_PATH)
        ,
        args = {
                @Argument(name = "target", type = TypeKind.OBJECT, structType = Constants.PATH_STRUCT,
                          structPackage = Constants.PACKAGE_PATH)
        },
        returnType = {
                @ReturnType(type = TypeKind.OBJECT, structType = BLangVMErrors.STRUCT_GENERIC_ERROR,
                            structPackage = BLangVMErrors.PACKAGE_BUILTIN)
        },
        isPublic = true
)
public class MoveTo extends BlockingNativeCallableUnit {
    
    private static final Logger log = LoggerFactory.getLogger(MoveTo.class);
    
    @Override
    public void execute(Context context) {
        BStruct sourcePathStruct = (BStruct) context.getRefArgument(0);
        Path sourcePath = (Path) sourcePathStruct.getNativeData(Constants.PATH_DEFINITION_NAME);
        
        BStruct targetPathStruct = (BStruct) context.getRefArgument(1);
        Path targetPath = (Path) targetPathStruct.getNativeData(Constants.PATH_DEFINITION_NAME);
    
        File source = new File(sourcePath.toString());
        File target = new File(targetPath.toString());
        try {
            FileUtils.moveDirectory(source, target);
        } catch (IOException ex) {
            String msg = "IO error occurred while moving file/directory from: \'" + sourcePath + "\' to: \'" +
                         targetPath + "\'";
            log.error(msg, ex);
            context.setReturnValues(BLangVMErrors.createError(context, msg));
        }
    }
}
