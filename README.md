# synapse-plugin
Dockstore Synapse file provisioning plugin

## Usage 

This provision method only supports download for now. 

```
$ cat test.synapse.json                                                                                                                                            
{
  "input_file": {
        "class": "File",
        "path": "syn://syn8299856"
    },
    "output_file": {
        "class": "File",
        "path": "/tmp/md5sum.txt"
    }
}

$ dockstore tool launch --entry  quay.io/briandoconnor/dockstore-tool-md5sum  --json test.synapse.json                                                             
Creating directories for run of Dockstore launcher at: ./datastore//launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4
Provisioning your input files to your local machine
Downloading: #input_file from syn://syn8299856 into directory: /media/large_volume/dockstore_tools/dockstore-tool-md5sum/./datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/inputs/990720a6-d5c8-4ef2-b015-12
c575a8e7f4
Found file syn://syn8299856 in cache, hard-linking
Calling on plugin io.dockstore.provision.SynapsePlugin$SynapseProvision to provision syn://syn8299856
Calling out to cwltool to run your tool
Executing: cwltool --enable-dev --non-strict --outdir /media/large_volume/dockstore_tools/dockstore-tool-md5sum/./datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/outputs/ --tmpdir-prefix /media/large_volu
me/dockstore_tools/dockstore-tool-md5sum/./datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/tmp/ --tmp-outdir-prefix /media/large_volume/dockstore_tools/dockstore-tool-md5sum/./datastore/launcher-14c0bd4c-
821b-49a0-8b14-2ee2085038a4/working/ /tmp/1488401341483-0/temp7036304455702546523.cwl /media/large_volume/dockstore_tools/dockstore-tool-md5sum/./datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/workflow_p
arams.json
/usr/local/bin/cwltool 1.0.20170217172322
Resolved '/tmp/1488401341483-0/temp7036304455702546523.cwl' to 'file:///tmp/1488401341483-0/temp7036304455702546523.cwl'
[job temp7036304455702546523.cwl] /media/large_volume/dockstore_tools/dockstore-tool-md5sum/datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/working/XDSteC$ docker \
    run \
    -i \
    --volume=/media/large_volume/dockstore_tools/dockstore-tool-md5sum/./datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/inputs/990720a6-d5c8-4ef2-b015-12c575a8e7f4/syn8299856:/var/lib/cwl/stgb2cf6387-89a
1-4254-947d-2df8cf6cc6df/syn8299856:ro \
    --volume=/media/large_volume/dockstore_tools/dockstore-tool-md5sum/datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/working/XDSteC:/var/spool/cwl:rw \
    --volume=/media/large_volume/dockstore_tools/dockstore-tool-md5sum/datastore/launcher-14c0bd4c-821b-49a0-8b14-2ee2085038a4/tmp/s1MaIt:/tmp:rw \
    --workdir=/var/spool/cwl \
    --read-only=true \
    --user=1000 \
    --rm \
    --env=TMPDIR=/tmp \
    --env=HOME=/var/spool/cwl \
    quay.io/briandoconnor/dockstore-tool-md5sum:1.0.2 \
    /bin/my_md5sum \
    /var/lib/cwl/stgb2cf6387-89a1-4254-947d-2df8cf6cc6df/syn8299856
[job temp7036304455702546523.cwl] completed success

```

