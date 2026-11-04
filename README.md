### git-project-Jake
instantiate()
hashFile()
createBlob()

## GP-3.0 done by Yari:

# createWorkingList(File fileList)
Takes in a file the contents of which are a list of files in the style of the index. The method goes line by line using a BufferedReader, writing each line into a list which will be sorted later. The method then creates a simple comparator which compares two lines from the index by their file paths. The list is then sorted using that comparator and its contents are written into a new workingList file. Because all of the paths in the list are BLOBs, "blob" is attached when they are written to the working list to identify them as such.

# findLeafMostDirectory(File workingList)
Takes in a working list and goes through it finding the deepest directory in it. Returns the deepest directory

# createTree(File workingList, String dirPath)
Takes in a working list and the directory with which to create a tree object. The method goes through the working list and collects all the paths which share the same directory path as dirPath. A new file called in git/objects called newTree is created and all of the paths are written into it. The file is hashed and is renamed to that hash. The has is returned.

# rewriteWorkingList(File workingList, String treehash, String dir)
Takes in a working list, a hash of a tree, and its directory. Goes through the working and finds all instances of that directory path in the index. The first time this is the case, the line is rewritten to be the tree hash and the directory path, consolidating the working list entry under a tree object. All other instances are just not written as the tree object entry encloses all instances of the directory path. 

# createTreesFromIndex()
Utilizes a recursive loop to create trees from the index file. It first creates the working list. In the actual loop, it first finds the leaf most directory using findLeafMostDirectory, then creates a tree using createTree and that leaf most directory, and then rewrites the working list using rewriteWorkingList to consolidate all the instances of that leaf most directory under a singular tree entry. This continues until only the root directory remains, and its hash is returned.
#tester
it works the code stages files, creates  blobs and trees it  checks every reachable object against the files bytes. tree format and SHA-1 are there.
#found bugs 
text reader removed parts from blobs
no root tree saved
root level has no slash
tree holds full path 
start of filename used as path
same file name duplicated in index
#root
1dc938f31da357b7c18967326a54d42259b9fc89
inside root
blob 31f54e4945289417fd3e5e6a742df65af19cf52a binary.dat
blob da39a3ee5e6b4b0d3255bfef95601890afd80709 empty.txt
tree 8329a0343fca8b31a2bbcbcc179ce20ae132132f layer1
tree 3cbba6cb3c4a40acbf85fe176e8a16677e7df6df my docs
tree 575677f9c05bc8e27764eab52e322a86fde1c719 notes
blob e8b4cf0eed00c304163b11f17aff048ad25df5c9 snapshot.txt
inside layer 1 
blob 306a5cc1ad8911f4840b3359bf1b2fa7967b6d7f README.md
tree e560b0d727800ebd2f46e9f18a7372404ee853ec layer2
inside layer 2 
blob 0a4d55a8d778e5022fab701977c5d840bbc486d0 Hello.txt
blob faa57ddbbae64f4de24297f0eb7ea243027f086a entry.txt
inside mydocs 
blob d1c6b3896e42081058586da2eb6b05f2a5567467 my file.txt
inside notes 
blob 2b4e75a0797f761699ea67178938a10c79ac7a87 Hello.txt

all of these are formated correct for index and running from a sha-1 hash