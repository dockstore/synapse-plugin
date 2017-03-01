/*
 *    Copyright 2016 OICR
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.dockstore.provision;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.sagebionetworks.client.SynapseClient;
import org.sagebionetworks.client.SynapseClientImpl;
import org.sagebionetworks.repo.model.file.FileHandleAssociateType;
import org.sagebionetworks.repo.model.file.FileHandleAssociation;
import org.sagebionetworks.repo.model.file.FileHandleAssociationProvider;
import ro.fortsoft.pf4j.Extension;
import ro.fortsoft.pf4j.Plugin;
import ro.fortsoft.pf4j.PluginWrapper;
import ro.fortsoft.pf4j.RuntimeMode;

/**
 * @author dyuen
 */
public class SynapsePlugin extends Plugin {

    public SynapsePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        // for testing the development mode
        if (RuntimeMode.DEVELOPMENT.equals(wrapper.getRuntimeMode())) {
            System.out.println(StringUtils.upperCase("SynapsePlugin development mode"));
        }
    }

    @Override
    public void stop() {
        System.out.println("SynapsePlugin.stop()");
    }

    @Extension
    public static class SynapseProvision implements ProvisionInterface {

        private Map<String, String> config;

        public void setConfiguration(Map<String, String> map) {
            this.config = map;
        }

        public Set<String> schemesHandled() {
            return new HashSet<>(Lists.newArrayList("syn"));
        }

        public boolean downloadFrom(String sourcePath, Path destination) {
            SynapseClient synapseClient = new SynapseClientImpl();
            try {
                String synapseKey = config.get("synapse-api-key");
                String synapseUserName = config.get("synapse-user-name");
                synapseClient.setApiKey(synapseKey);
                synapseClient.setUsername(synapseUserName);
                // TODO: implement listener and show progress
                // ambiguous how to reference synapse files, rip off these kinds of headers
                if (sourcePath.startsWith("syn://syn")){
                    sourcePath = sourcePath.substring("syn://syn".length());
                }
                if (sourcePath.startsWith("syn://")){
                    sourcePath = sourcePath.substring("syn://".length());
                }
                // FileHandleAssociation assoc = new FileHandleAssociation();
                // assoc.setFileHandleId(sourcePath);
                // assoc.setAssociateObjectId(sourcePath);
                // assoc.setAssociateObjectType(FileHandleAssociateType.FileEntity);
                // doesn't work synapseClient.downloadFile(assoc, destination.toFile());

                // the following method is deprecated but the replacement doesn't seem to work
                synapseClient.downloadFromFileEntityCurrentVersion(sourcePath, destination.toFile());
                return true;
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw new RuntimeException("Could not provision input files from Synapse", e);
            }
        }

        public boolean uploadTo(String destPath, Path sourceFile, Optional<String> metadata) {
            // TODO: hook up metadata
            throw new UnsupportedOperationException("Synapse upload not implemented yet");
        }

    }
}

