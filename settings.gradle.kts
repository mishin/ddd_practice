rootProject.name = "ddd_practice"

include(":application")
include(":common:types")
include(":common:telnet")
include(":common:events")

include(":shop:domain")
include(":shop:usecase")
include(":shop:in-memory-persistence")
include(":shop:telnet")
include(":shop:web")
include(":shop:payment")
include(":shop:rest")

include(":kitchen:domain")
include(":kitchen:usecase")
include(":kitchen:persistence")
include(":kitchen:web")
include(":kitchen:rest")

include(":delivery") // should be empty
include("common:rest")
findProject(":common:rest")?.name = "rest"
