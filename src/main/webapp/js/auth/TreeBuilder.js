class TreeBuilder {
    constructor(data) {
        this.makeTree(data);
    }

    makeTree(list) {
        // for(x in list){
        //     alert(list[x].id+":"+list[x].pid+":"+list[x].roledesc);
        // }
        const root = {};
        root.level = 0;

        // 建立 ID 到节点数据的映射表
        const map = list.reduce((nodes, node) => {
            nodes[node.id] = node;
            return nodes;
        }, {});

        // for(x in map){
        //     var item = map[x];
        //     alert("x="+x+","+item.id+":"+item.pid+":"+item.roledesc);
        // }

        // 通过映射表查找 pid，找到父节点，建立树结构
        // list.forEach(node => {
        //     const parent = map[node.pid] || root;
        //     const children = parent.children = parent.children || [];
        //     children.push(node);
        // });
        for(x in list){
            var node = list[x];
            const parent = map[node.pid] || root;
            const children = parent.children = parent.children || [];
            node.level = parent.level + 1;
            children.push(node);
        }

        this.tree = root;
        // this.printtree(this.tree, 0);
    }

    // printtree(root,floor){
    //     alert("in "+floor+":"+root.id+";"+root.pid+";"+root.roledesc+";"+root.level);
    //     var children = root.children;
    //     for(x in children){
    //         this.printtree(children[x], floor+1);
    //     }
    // }

    build() {
        // buildChildren 和 buildNode 是双函数的递归实现
        return this.buildChildren(this.tree);
    }

    /**
     * 处理子节点
     */
    buildChildren(parent) {
        // const $ul = $("<ul>");
        // parent.children
        //     .map(node => this.buildNode(node))
        //     .forEach(node => $ul.append(node));
        // return $ul;
        var children = parent.children;
        const $ul = $("<ul>");
        $ul.css({
            "padding-left": parseInt(parent.level) * 10 + 'px'
        });
        for(x in children){
            var child = children[x];
            // console.debug("in buildChildren,building:"+child.id);
            $ul.append(this.buildNode(child));
        }
        return $ul;
    }

    /**
     * 处理节点，若有子节点则调用 buildChildren 生成子树
     */
    buildNode(node) {
        // const $li = $("<li>").append(
        //     $("<a>").text(node.roledesc || "")
        //         .attr("href", node.url || "#"));
        var $label_symbol;
        if (node.children && node.children.length) {
            $label_symbol = $("<label>").attr("id", "role-symbol-"+node.id).text(" + ");
        }else{
            $label_symbol = $("<label>").attr("id", "role-symbol-"+node.id).text(" * ");
        }
        const $li = $("<li>")
            .append($label_symbol)
            .append($("<label>").attr("id", "role-text-"+node.id).text(node.roledesc || "unname"));
            $("#roles-container").on("click", "#role-symbol-"+(node.id), function () {
                var id = (this.id.split("-"))[2];
                var span = document.getElementById("role-span-"+id);
                var text = this.innerHTML.trim();
                if(text == "+"){
                    span.style.display = "block";
                    this.innerHTML = " - ";
                }
                else if(text == "-") {
                    span.style.display = "none";
                    this.innerHTML = " + ";
                }
            });
            $("#roles-container").on("click", "#role-text-"+(node.id), function () {
                var obj = document.getElementById("ul-permissions");
                var childs = obj.getElementsByTagName("li")
                for (var x1 in childs) {
                    var child_childs = childs[x1].childNodes;
                    for (var c in child_childs) {
                        if (child_childs[c].type == "checkbox") {
                            child_childs[c].removeAttribute("disabled");
                            child_childs[c].checked = false;
                        }
                    }
                }
                $("label").css("background", "#00000000");
                $(this).css("background", "#888888");
                var id = (this.id.split("-"))[2];
                getFatherRolePermission(id);
                getRolePermission(id);
            });
        if (node.children && node.children.length) {
            // $li.append(this.buildChildren(node));
            const $span = $("<span>")
                .attr("id", "role-span-"+node.id)
                .css({
                    "display" : "none"
                });
            $span.append(this.buildChildren(node));
            $li.append($span);
        }
        // console.debug("in buildNode,building:"+node.id+":"+$li);
        return $li;
    }
}